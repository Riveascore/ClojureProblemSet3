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

;(println
;  (with-out-str
;		(time
;			(tri-gram (fileToSeq "src/files/Act1Prologue.txt") {}))))
   

(defn tri-grams-from-files [& args]
  (def trigrams
    (map #(tri-gram
            (fileToSeq %1)
            {}
          )
         args))
  
  
  (reduce
    #(merge-with
       +
       %1
       %2
    )
    {}
    trigrams
  )
)

(defn parallel-tri-grams-from-files [& args]
  (def trigrams
    (pmap #(tri-gram
            (fileToSeq %1)
            {}
          )
         args))
  
  
  (reduce
    #(merge-with
       +
       %1
       %2
    )
    {}
    trigrams
  )
)


(println
  (with-out-str
		(time
			(tri-grams-from-files 
			  "src/midsummer/act1Scene1.txt" 
			  "src/midsummer/act1Scene2.txt" 
			  "src/midsummer/act2Scene1.txt" 
			  "src/midsummer/act2Scene2.txt" 
			  "src/midsummer/act3Scene1.txt" 
			  "src/midsummer/act3Scene2.txt" 
			))))

(println
  (with-out-str
		(time
			(parallel-tri-grams-from-files
			  "src/midsummer/act1Scene1.txt" 
			  "src/midsummer/act1Scene2.txt" 
			  "src/midsummer/act2Scene1.txt" 
			  "src/midsummer/act2Scene2.txt" 
			  "src/midsummer/act3Scene1.txt" 
			  "src/midsummer/act3Scene2.txt" 
			))))