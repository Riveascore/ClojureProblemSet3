;(conj [] 1 2 3 4)
;
;
;(reduce (fn [counts c]
;              (assoc! counts c
;                      (inc (get counts c 0)))) 
;            (transient {})
;            s
;)
;
;
;(defn tri-gram [input ourMap]
;  (if (> (count input) 2)
;    (tri-gram
;      (rest input)
;      (assoc
;        ourMap
;        (tri input)
;        (inc (get ourMap (tri input) 0))
;      )
;    )
;    (tri-gram
;      (rest input)
;      ourMap
;    )
;  )
;)


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

(defn tri-grams-from-files [& args]
  (tri-gram
    (clojure.string/split
	    (reduce 
		    #(str %1 (slurp %2))
		    ""
		    args
		  )
      #" "
    )
    {}
  )
)

(tri-grams-from-files "src/files/Act1Prologue.txt" "src/files/Act2Prologue.txt" "src/files/Act3Prologue.txt")
