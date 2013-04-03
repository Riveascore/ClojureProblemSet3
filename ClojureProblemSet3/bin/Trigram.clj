(defn tri [input]
  (conj []
        (first input)
        (second input)
        (nth input 2)
  )
)

(defn changeMap [input ourMap]
  (assoc 
    ourMap
    (tri input)
    (inc (get ourMap (tri input) 0))
  )
)

(defn tri-gram [input ourMap]
  (if (> (count input) 2)
    (tri-gram 
      (rest input)
      (changeMap
        input
        ourMap
      )
    )
    ourMap
  )
)

(defn fileToSeq [fileName]
  (def myString (slurp fileName))
  (re-seq #"[a-zA-Z]+(?:\-?[a-zA-Z]+|\'?[a-zA-Z]*)" myString)
)

(tri-gram (fileToSeq "src/files/Act1Prologue.txt") {})
            
(defn tri-grams-from-files [& args]
  (reduce
    #(merge-with
       +
       %1
       (tri-gram (fileToSeq %2) {})
    )
    {}
    args
  )
)

(tri-grams-from-files 
  "src/midsummer/act1Scene1.txt" 
  "src/midsummer/act1Scene2.txt" 
  "src/midsummer/act2Scene1.txt" 
  "src/midsummer/act2Scene2.txt" 
  "src/midsummer/act3Scene1.txt" 
  "src/midsummer/act3Scene2.txt" 
)





