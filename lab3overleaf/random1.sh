STATES="random"
SIZES="10000 20000 30000 40000 50000"

for SIZE in $SIZES
do
echo $SIZE
for COUNT in 1 2 3 4 5
do
  python3 generate.py data/large/henry/dict experiment/random_${SIZE}$COUNT experiment/query${SIZE}_$COUNT $SIZE 0 $STATES 0
done
done