import re
import argparse

def read_file(file_path):
    content = open(file_path,'r').read()
    return content
def remove_from_file(text):
    #eliminarea semnelor de punctuatie, spatiilor multiple si transformarea literelor mari in litere mici
    text = ' '.join( ( (re.sub( r'[^\w\s]', '', text) ).lower().split() ) ) 
    return text

def main():
    parser = argparse.ArgumentParser(description="")
    parser.add_argument('file_path', type=str, help="input file")
    args = parser.parse_args()

    #citire si afisare
    content = read_file(args.file_path)
    print("fisierul original:")
    print(content+"\n")

    proc_text = remove_from_file(content)
    print("fisierul procesat:")
    print(proc_text+"\n")

if __name__ == "__main__":
    main()


