(defn notTransform [ourVector ourMap]
  (not (get ourMap (nth ourVector 1)))
)

(defn transformBasedOnType [object inputMap]
  (if (= (type object) (type []))
    (notTransform object inputMap)
    (get inputMap object)
  )
)

(defn transformElements [inputVector inputMap]
  (pmap #(transformBasedOnType % inputMap) inputVector)
)

(defn orTransformation [ourVector, ourMap]
  (reduce #(or %1 %2) (transformElements ourVector ourMap))
)

(defn satEvaluation [inputMap inputVector]
  (reduce
    #(and %1 %2)
    (pmap #(orTransformation % inputMap) inputVector)
  )
)

(defn check-assignment-list [fullVector inputMaps]
  (pmap #(satEvaluation % fullVector) inputMaps)
)

(def bigVector [ [:a :b :c] [[:not :a] :b [:not :c]] [:a :b [:not :c]] [:a [:not :b] :c] ])
(def mapOfMaps [{:a true, :b false, :c true}, {:a true, :b false, :c false}, {:a false, :b true, :c true}])

(check-assignment-list
  bigVector
  mapOfMaps
)