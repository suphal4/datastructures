STATES="sorted none"
SIZES="10000 20000 30000 40000 50000"

rm data/exp_2_sorted.dat

rm data/exp_2_none.dat
rm data/exp_2_sorted.csv

rm data/exp_2_none.csv


for STATE in $STATES
do
for SIZE in $SIZES
do
for COUNT in 1 2 3 4 5
do
  # Debugging statement to check program calls working as expected
  #java comp26120.speller_bstree -d experiment1/${STATE}${SIZE}_$COUNT -m 4 -s ${SIZE} experiment1/query${STATE}_${SIZE}_$COUNT

  ALL_TIME=`(time -p java speller_bstree -d experiment1/${STATE}${SIZE}_$COUNT -m 4 -s ${SIZE} experiment1/query${STATE}_${SIZE}_$COUNT) 2>&1 | grep -E "user|sys" | sed s/[a-z]//g`

  RUNTIME=0
  for i in $ALL_TIME;
  do RUNTIME=`echo $RUNTIME + $i|bc`;
  done
  echo $SIZE $RUNTIME >> data/exp_2_${STATE}.dat
  echo $SIZE ,$RUNTIME >> data/exp_2_${STATE}.csv
done

done

done