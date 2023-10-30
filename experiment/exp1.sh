SIZES="10000 20000 30000 40000 50000"

rm data/exp1.dat
rm data/exp1.csv

for SIZE in $SIZES
do
for COUNT in 1 2 3 4 5
do
  # Debugging statement to check program calls working as expected
 # python3 python/speller_hashset.py -d experiment/random_${SIZE}$COUNT -m 4 -s ${SIZE} experiment/query${SIZE}_$COUNT

  ALL_TIME=`(time -p java speller_hashset -d experiment/random_${SIZE}$COUNT -m 4 -s ${SIZE} experiment/query${SIZE}_$COUNT) 2>&1 | grep -E "user|sys" | sed s/[a-z]//g`

  RUNTIME=0
  for i in $ALL_TIME;
  do RUNTIME=`echo $RUNTIME + $i|bc`;
  done
  echo $SIZE $RUNTIME >> data/exp1.dat
  echo $SIZE ,$RUNTIME >> data/exp1.csv
done

done