#!/bin/sh


set -x

<<!
	麻烦师兄把python2.7 换成 anconda下边的python2.7: 例如: /Users/didi/anaconda/bin/python2.7
!
rm -r 大师
python2.7 unzip_info.py 大师.zip


rm -r 大师_translate
# 分别对四个文件进行转换.
java -classpath ai_chess.jar bridge.loader.ChessGenerator 大师   大师_translate

rm -f dashi_raw.txt
python2.7 get_raw_data.py 大师_translate dashi_raw.txt

rm -r ceshi_trans

tar xzvf ceshi.tar.gz

# 分别对四个文件进行转换.
java -classpath ai_chess.jar bridge.loader.ChessGenerator ceshi_trans ceshi_translate

rm -rf ceshi_raw.txt
python2.7 get_raw_data.py ceshi_translate ceshi_raw.txt


cat ceshi_raw.txt dashi_raw.txt > raw_data.txt

java -classpath ai_chess.jar bridge.loader.FeatureGenerator2 raw_data.txt sample.txt

python2.7 lr_train.py sample.txt 1>model.txt 2>err.txt



