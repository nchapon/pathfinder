(ns pathfinder.core)


(defn neighbors
  ([size xy] (neighbors [[-1 0] [1 0] [0 -1] [0 1]] size xy))
  ([deltas size xy]
     (filter (fn [new-xy]
               (every? #(< -1 % size) new-xy))
             (map #(mapv + xy %) deltas))))

(defn estimate-cost
  [step-cost-est size x y]
  (* step-cost-est
     (- (+ size size) x y 2)))

(defn path-cost
  [node-cost cheapest-nbr]
  (+ node-cost
     (:cost cheapest-nbr 0)))

(defn total-cost
  [new-cost step-cost-est size x y]
  (+ new-cost
     (estimate-cost step-cost-est size x y)))


(defn min-by
  [f coll]
  (when (seq coll)
    (reduce (fn [min this]
              (if (> (f min) (f this)) this min))
            coll)))


(defn A* [start-yx est-cost cell-costs]
  (let [size (count cell-costs)]
    (loop [steps 0
           routes (vec (repeat size (vec (repeat size nil))))
           work-todo (sorted-set [0 start-yx])]
         ;; (println "----------------------------")
         ;; (println "routes:" routes)
         ;; (println "work-todo:" work-todo)
      (if (empty? work-todo)
        [(peek (peek routes)) :steps steps]
        (let [[_  yx :as work-item] (first work-todo)
              rest-work-todo (disj work-todo work-item)
              nbr-yxs (neighbors size yx)
              cheapest-nbr (min-by :cost
                                   (keep #(get-in routes %)
                                         nbr-yxs))
              newcost (path-cost (get-in cell-costs yx) cheapest-nbr)
              oldcost (:cost (get-in routes yx))]
          ;;(println "nbr-yxs: " nbr-yxs)
          (if (and oldcost (>= newcost oldcost))
            (recur (inc steps) routes rest-work-todo)
            (recur (inc steps)
                   (assoc-in routes yx
                              {:cost newcost
                               :yxs (conj (:yxs cheapest-nbr [])
                                          yx)})
                   (into rest-work-todo
                         (map
                          (fn [w]
                            (let [[y x] w]
                              [(total-cost newcost est-cost size y x) w]))
                          nbr-yxs)))))))))
