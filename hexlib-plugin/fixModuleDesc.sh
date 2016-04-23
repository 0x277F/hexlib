#!/usr/bin/env bash
# Because of the extremely sketchy way in which I inject my
# plugin version into the source, this is required to modify
# my IntelliJ module files to unmark the templated sources
# as a sources root.
basedir=`pwd`
[[ ! -f "$basedir/hexlib-plugin.iml" ]] && exit 0 || echo "Fixing IntelliJ module descriptions..."
if [ -d "$basedir/target/generated-sources/java-templates" ]; then
    newfile=`sed '/generated-sources/d' $basedir/hexlib-plugin.iml`
    rm "$basedir/hexlib-plugin.iml"
    echo "$newfile" > "$basedir/hexlib-plugin.iml"
fi
