#coding=utf-8

# 5792个特征.

from sklearn import datasets as ds 
from sklearn.linear_model import LogisticRegression  

from sklearn.neural_network import BernoulliRBM
import sys


def load_data():
    x_train,y_train = ds.load_svmlight_file(sys.argv[1], 6816 * 2)
    return x_train, y_train


x_train,y_train = load_data()


classifier = LogisticRegression(verbose=1, max_iter=100, n_jobs=8)

#classifier = BernoulliRBM(n_components=2,verbose=1)

classifier.fit(x_train, y_train)

# coef

for c in classifier.coef_:
    print ",".join(map(str, c))

for i in classifier.intercept_:
    print i


'''
print "intercept_hidden_"
print classifier.intercept_hidden_

print "intercept_visible_"
print classifier.intercept_visible_

print "components_"
print classifier.components_

'''
