# String search

**Application for finding strings in text**

## Configuration

*Output: indexes of inputted DNA genomes*

*Note: recommended to set more memory (`-Xmx8192m`) with big files*

```
    algorithm dna-file [genomes]
    
    dna-file: path to the DNA file
    algorithm: pt  - prefix tree
               kwt - keyword tree
               testPT  - measure time for prefix tree
               testKWT - measure time for keyword tree
    [genomes]: array of DNA genomes 
    
    Example: pt assets\dna\dna1.txt CCGCGC CACAGC CCGCGA
```
 
