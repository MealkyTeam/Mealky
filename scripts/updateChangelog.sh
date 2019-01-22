#!/usr/bin/env bash
tag=`git describe --tags \`git rev-list --tags --max-count=1\``

eval "git log --no-merges --pretty=format:\"%an %s\" $tag..develop > $1/config/release-notes.txt"
