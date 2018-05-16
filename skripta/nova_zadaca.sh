#!/bin/bash
# 1 argument is number of homework
homework_number=$1

# gets folder name
if (( homework_number < 10 ))
then
	folder_name="hw0"$homework_number"-0036485822"
else
	folder_name="hw"$homework_number"-0036485822"
fi

# checks if folder already exists
if [ -e "../$folder_name" ]
then
	echo "folder $folder_name already exists, exiting..."
	exit 1
fi

# creates folder and in it desired structure
$(mkdir ../$folder_name)
$(mkdir ../$folder_name/src)
$(mkdir ../$folder_name/src/main)
$(mkdir ../$folder_name/src/test)
$(mkdir ../$folder_name/src/main/java)
$(mkdir ../$folder_name/src/main/resources)
$(mkdir ../$folder_name/src/test/java)

# change pom.xml and copy it to homework folder
$(sed -i "s/hw[0-9]*-0036485822/$folder_name/" pom.xml)
$(cp pom.xml ../$folder_name)

echo "created $folder_name"

