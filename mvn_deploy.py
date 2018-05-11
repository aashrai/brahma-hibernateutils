#!/usr/bin/env python
from __future__ import print_function
import os
import sys
import argparse
from subprocess import call
import untangle
from boto.s3.connection import S3Connection
from boto.s3.key import Key


BUCKET_NAME = "zefo-maven-repo"
KEY_PATH_RELEASE = "releases"
KEY_PATH_SNAPSHOTS = "snapshots"


def get_jar_path():
    pom = untangle.parse("pom.xml")
    artifact_id = pom.project.artifactId.cdata
    artifact_version = pom.project.version.cdata
    artifact_version = artifact_version.split('-')[0]
    artifact_groupid = pom.project.groupId.cdata
    artifact_groupid = artifact_groupid.replace(".", "/")
    key_path = KEY_PATH_RELEASE
    key_name = "{}/{}/{}/{}/{}-{}.jar".format(key_path, artifact_groupid,
                                              artifact_id, artifact_version,
                                              artifact_id, artifact_version)
    print("Jar -> {}".format(key_name))
    return key_name


def artifact_exists():
    conn = S3Connection(os.environ['AWS_MAVEN_ACCESS_KEY'],
                        os.environ['AWS_MAVEN_SECRET_ACCESS_KEY'])
    bucket = conn.get_bucket(BUCKET_NAME)

    key_name = "{}".format(get_jar_path())
    key = Key(bucket, key_name)

    if bucket.get_key(key.name):
        return True
    return False


def deploy():
    call("mvn clean deploy", shell=True)


def main():
    parser = argparse.ArgumentParser(description="Maven deployment script")
    parser.add_argument('-s', '--snapshot', help="Snapshot deployment",
                        action='store_true')
    parser.add_argument('-r', '--release', help="Release deployment",
                        action='store_true')
    args = vars(parser.parse_args())
    if args['release'] and not args['snapshot']:
        release = True
    elif not args['release'] and args['snapshot']:
        release = False
    else:
        print("Invalid arguments.")
        sys.exit(1)

    if not release:
        print("Deploying snapshot artifact on maven repo.")
        deploy()
    elif artifact_exists():
        print("Artifact already exist. Please increment version" +
              "and deploy again.")
    else:
        print("Deploying release artifact on maven repo.")
        deploy()


if __name__ == "__main__":
    main()
