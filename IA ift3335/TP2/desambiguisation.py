##################################################
##### auteur: Mike useni et soumaila keita #######
#################packages#########################
import re
import numpy as np
from sklearn.preprocessing import LabelEncoder
from sklearn.preprocessing import OneHotEncoder

from nltk.stem import LancasterStemmer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import MultinomialNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.neural_network import MLPClassifier
from sklearn.utils import column_or_1d


#Arrange le teste en ligne de phase
def phraseTab(item):
    text = ""
    for k in item:
        x = re.sub(r"[^a-zA-Z0-9/$ ]","",k)
        text += x
    return text.split("$$")

#retourne un tab de stopeWord
def stopeWord(item):
    text1 = ""
    for k in tabStop:
        text1 += k
    regex = re.compile(r'[\n\r\t]')
    text1 = regex.sub(" ", text1)
    return text1.split(" ")

#returne un tab 2D
def tokenization(phraseV):
    txt2 = []
    for l in range(len(phraseV)):
        temp = phraseV[l].split(" ")
        # print(temp)
        for p in range(len(temp)):
            txt = []
            x = re.search("^interest[0-9]", temp[p])
            if x:

                n2 = p - 2
                if (n2 > 0):
                    txt.append(temp[n2])
                else:
                    txt.append("/")

                n2 = p - 1
                if (temp[n2] == ''):
                    n2 = n2 - 1
                if (n2 > 0):
                    txt.append(temp[n2])
                else:
                    txt.append("/")

                n = p + 2
                if (n < len(temp) and temp[n] != ''):
                    txt.append(temp[n])
                else:
                    txt.append("/")

                n = p + 1
                if (temp[n] == ''):
                    n = n + 1
                if (n < len(temp)):
                    txt.append(temp[n])
                else:
                    txt.append("/")

                txt.append(temp[p])
                txt2.append(txt)

    return txt2

#supprimer les stopsWord
def deleteStop(tabStop):
    tabStop = stopeWord(tabStop)
    # print(tabStop)
    tabSansStop = ""
    tabSansStop2 = []
    for j in phraseV:
        # print(j)
        for m in j.split(" "):
            # print(m)
            s = m.split("/")
            if s[0] not in tabStop:
                # print(s[0])
                tabSansStop += m
                tabSansStop += " "
        tabSansStop += "\n"
    return tabSansStop.split("\n")

#divise les mots des cats
def divMot_cat(data):
    tab2 = []
    for p in range(len(data)):
        dataB = []
        for i in range(len(data[p])):
            tab = data[p][i].split("/")

            for j in tab:
                dataB.append(j)
        if (len(dataB) > 9):
            tab2.append(dataB)
    return tab2

def lemmatisation(tab2):
    for p in range(len(tab2)):
        lancaster = LancasterStemmer()
        tab2[p][0]=lancaster.stem(tab2[p][0])
        tab2[p][2]=lancaster.stem(tab2[p][2])
        tab2[p][4]=lancaster.stem(tab2[p][4])
        tab2[p][6]=lancaster.stem(tab2[p][6])

    return tab2

if __name__ == "__main__":
    ##############charge les donnes ##########################
    fichier = open("interest.acl94.txt")
    line = fichier.readlines()
    fichier.close()

    fichier = open("stoplist-english.txt")
    tabStop = fichier.readlines()
    fichier.close()

    phraseV = phraseTab(line)

    ############# traitement des donnes ##########################
    tabSansStopV = deleteStop(tabStop)
    data = tokenization(tabSansStopV)
    data2 =divMot_cat(data)
    dataA=np.array(lemmatisation(data2))

    ############# transformation  ##########################
    dataB = column_or_1d(dataA[:,8:9].ravel(), warn=True)
    transformer = LabelEncoder()
    y=transformer.fit_transform(dataB)

    transformerX = OneHotEncoder()
    X=transformerX.fit_transform(dataA[:,0:8])

    ############# split des donnes ##########################
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.7)

######################## naive bayes #######################
    #OneHotEncoder
    naive_bayes = MultinomialNB()
    fit_NB = naive_bayes.fit(X_train,y_train)
    predi = fit_NB.predict(X_test)

    print("-------naive bayes-----------")
    print(f"nb tests:{(predi == y_test).sum()}")
    print(f"resuletat:{(predi==y_test).sum()/len(y_test)}")
#############################################################

########### DecisionTreeClassifier ############################
    tree = DecisionTreeClassifier()
    fit_tree = tree.fit(X_train, y_train)
    predi = fit_tree.predict(X_test)

    print("------- tree -----------")
    print(f"nb tests:{(predi == y_test).sum()}")
    print(f"resuletat:{(predi==y_test).sum()/len(y_test)}")
###############################################################

############# neural_network ############################
    clf = MLPClassifier(random_state=1, max_iter=300)
    fit_clf = clf.fit(X_train, y_train)
    predi = fit_clf.predict(X_test)

    print("-------neural_network (100)-----------")
    print(f"nb tests:{(predi == y_test).sum()}")
    print(f"resuletat:{(predi == y_test).sum() / len(y_test)}")
    ##

    clf = MLPClassifier(random_state=1, max_iter=300, hidden_layer_sizes=50)
    fit_clf = clf.fit(X_train, y_train)
    predi = fit_clf.predict(X_test)

    print("-------neural_network (50)-----------")
    print(f"nb tests:{(predi == y_test).sum()}")
    print(f"resuletat:{(predi == y_test).sum() / len(y_test)}")
    ##

    clf = MLPClassifier(random_state=1, max_iter=300, hidden_layer_sizes=200)
    fit_clf = clf.fit(X_train, y_train)
    predi = fit_clf.predict(X_test)

    print("-------neural_network (200)-----------")
    print(f"nb tests:{(predi == y_test).sum()}")
    print(f"resuletat:{(predi == y_test).sum() / len(y_test)}")
#############################################################