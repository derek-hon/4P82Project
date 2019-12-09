#!/bin/bash

#java ec.Evolve -file ec/Project4P82/smallFilters/islandSmall.params -p seed.0=572389 -p seed.1=849302 -p seed.2=32048 -p seed.3=43278
#cp out.stat ec/Project4P82/stat/islandSmall/small.stat
#
#java ec.Evolve -file ec/Project4P82/mediumFilters/islandMedium.params -p seed.0=214908 seed.1=9286734 seed.2=78912 seed.3=87241
#cp out.stat ec/Project4P82/stat/islandMedium/medium.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/islandBig.params -p seed.0=43728 seed.1=123789 seed.2=234890 seed.3=93812
#cp out.stat ec/Project4P82/stat/islandBig/big.stat
#
#
#java ec.Evolve -file ec/Project4P82/smallFilters/projectSmall.params -p seed.0=572389 -p seed.1=849302 -p seed.2=32048 -p seed.3=43278
#cp out.stat ec/Project4P82/stat/small/small.stat
#
#java ec.Evolve -file ec/Project4P82/mediumFilters/projectMedium.params -p seed.0=214908 seed.1=9286734 seed.2=78912 seed.3=87241
#cp out.stat ec/Project4P82/stat/medium/medium.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/projectBig.params -p seed.0=43728 seed.1=123789 seed.2=234890 seed.3=93812
#cp out.stat ec/Project4P82/stat/big/big.stat
for (( i = 1 ; i <= 10 ; i++)); do

java ec.Evolve -file ec/Project4P82/smallFilters/islandSmall.params -p seed.0=${i} -p seed.1=${i} -p seed.2=${i} -p seed.3=${i}
cp out.stat ec/Project4P82/stat/islandSmall/small_${i}.stat

java ec.Evolve -file ec/Project4P82/mediumFilters/islandMedium.params -p seed.0=${i} seed.1=${i} seed.2=${i} seed.3=${i}
cp out.stat ec/Project4P82/stat/islandMedium/medium_${i}.stat

java ec.Evolve -file ec/Project4P82/bigFilters/islandBig.params -p seed.0=${i} seed.1=${i} seed.2=${i} seed.3=${i}
cp out.stat ec/Project4P82/stat/islandBig/big_${i}.stat


java ec.Evolve -file ec/Project4P82/smallFilters/projectSmall.params -p seed.0=${i} -p seed.1=${i} -p seed.2=${i} -p seed.3=${i}
cp out.stat ec/Project4P82/stat/small/small_${i}.stat

java ec.Evolve -file ec/Project4P82/mediumFilters/projectMedium.params -p seed.0=${i} seed.1=${i} seed.2=${i} seed.3=${i}
cp out.stat ec/Project4P82/stat/medium/medium_${i}.stat

java ec.Evolve -file ec/Project4P82/bigFilters/projectBig.params -p seed.0=${i} seed.1=${i} seed.2=${i} seed.3=${i}
cp out.stat ec/Project4P82/stat/big/big_${i}.stat

#java ec.Evolve -file ec/Project4P82/island.params -p seed.0=${i} seed.1=${i+1} seed.2=${i+2} seed.3=${i+3} eval.problem.train=img2smallerfilters.txt eval.problem.test=img4smallerfilters.txt
#cp out.stat ec/Project4P82/C95M05/biggerfilters_seed_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/island.params -p seed.0=${i} seed.1=${i+1} seed.2=${i+2} seed.3=${i+3} eval.problem.train=img2.txt eval.problem.test=img4.txt
#cp out.stat ec/Project4P82/C95M05/smallerfilters_seed_${i}.stat

#java ec.Evolve -file ec/Project4P82/weight300.params -p seed.0=${i}
#cp out.stat ec/Project4P82/weight300/test_1_seed_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/weight500.params -p seed.0=${i}
#cp out.stat ec/Project4P82/weight500/test_1_seed_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/C9M1.params -p seed.0=${i}
#cp out.stat ec/Project4P82/C9M1/test_1_seed_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/C95M05.params -p seed.0=${i}
#cp out.stat ec/Project4P82/C95M05/test_1_seed_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/C975M025.params -p seed.0=${i}
#cp out.stat ec/Project4P82/C975M025/test_1_seed_${i}.stat
done