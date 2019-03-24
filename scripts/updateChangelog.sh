#!/usr/bin/env bash
`git fetch --tags`

tag=`git describe --tags --abbrev=0`
eval "git log --no-merges --date=short --pretty=format:\"%ad %an: %s\" ${tag}...HEAD > $1/config/release-notes.txt"
