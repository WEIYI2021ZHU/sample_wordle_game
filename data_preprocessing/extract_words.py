import tarfile
import os



# get the original file that contains all the words
path = "wn3.1.dict.tar.gz"


# use a set to temporarily save the 5-letter words
words = set()


with tarfile.open(path, "r:gz") as tar:
    for member in tar.getmembers():
        # use the index to extract the words
        if member.name.endswith(("index.noun", "index.verb", "index.adj", "index.adv")):
            file = tar.extractfile(member)
            if not file:
                continue
            for line in file:
                line = line.decode("utf-8").strip()
                if not line or line.startswith(" "):
                    continue

                word = line.split(" ")[0]
                word = word.lower().replace("_", "")
                # Keep only 5-letter alphabetic words
                if len(word) == 5 and word.isalpha():
                    words.add(word)


with open("five_letter_words.txt", "w") as f:
    for w in sorted(words):
        f.write(w + "\n")

print("Sucessfully!!!")
