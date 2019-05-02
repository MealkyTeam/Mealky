#!/usr/bin/env bash
PATH_TO_FILE=$1
FILE_NAME=$2

#Needs two argument: a path to file and filename where changelog will be printed into.
if [ "$#" -lt 2 ]; then
  echo 'Correct usage: ./script PATH_WHERE_CREATE_FILE FILENAME'
  exit 1
fi

if [ ! -d "$PATH_TO_FILE" ]; then
  echo "Path does not exists!"
  exit 1
fi

#Fetch all tags from remote repository.
`git fetch --tags`

#The command finds the most recent tag that is reachable from a commit.
#'--tags' fetch all tags event those not annotated.
#'--abbrev=0' find the closest tagname without any suffix.
#More in: https://git-scm.com/docs/git-describe
tag=`git describe --tags --abbrev=0`

#Print all commits (except merges) to file with short date in the format: %ad %an: %s.
#'%ad': author date 
#'%an': author name
#'%s': commit subject
#Example:
#2019-03-20 Uncle bob: Added new script for generating changelog
#More in: https://git-scm.com/docs/git-log
eval "git log --no-merges --date=short --pretty=format:\"%ad %an: %s\" ${tag}...HEAD > $PATH_TO_FILE/$FILE_NAME"
