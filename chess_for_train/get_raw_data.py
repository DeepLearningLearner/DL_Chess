#coding=utf-8

import sys
import os
import codecs

dirInfo = sys.argv[1]
outputFile = sys.argv[2]

outWriter = codecs.open(outputFile, "w")

for fileName in os.listdir(dirInfo):
	
	turn_color = 'b'
	for line in codecs.open(dirInfo + "/" + fileName, "r"):
		lines = line.strip()
		words = lines.split("\t")

		if len(words) == 1:
			break
		if words[1] == 'r' or words[1] == 'b':
			outWriter.write( words[0] + "\t" + turn_color + "\t" + words[1]	 + "\n")
			if turn_color == 'r':
				turn_color = 'b'
			else:
				turn_color = 'r'
		
outWriter.close()
