#!/bin/bash

for (( i = 1 ; i <= 10 ; i++)); do
seed=($RANDOM $RANDOM $RANDOM $RANDOM $RANDOM $RANDOM $RANDOM)

#java ec.Evolve -file ec/Project4P82/bigFilters/islandBig.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/islandBig/run_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/projectBig.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/big/run_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/islandBigNoADF.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/islandBigNoADF/run_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/bigNoADF.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/bigNoADF/run_${i}.stat

#java ec.Evolve -file ec/Project4P82/bigFilters/islandBig2.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/islandBig2/run_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/projectBig2.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/big2/run_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/islandBigNoADF2.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/islandBigNoADF2/run_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/bigNoADF2.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/bigNoADF2/run_${i}.stat

#java ec.Evolve -file ec/Project4P82/bigFilters/islandBig4.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/islandBig4/run_${i}.stat
#
#java ec.Evolve -file ec/Project4P82/bigFilters/projectBig2.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
#cp out.stat ec/Project4P82/stat/big4/run_${i}.stat

java ec.Evolve -file ec/Project4P82/bigFilters/islandBigNoADF4.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
cp out.stat ec/Project4P82/stat/islandBigNoADF4/run_${i}.stat

java ec.Evolve -file ec/Project4P82/bigFilters/bigNoADF4.params -p seed.0=${i} -p seed.1=${seed[0]} -p seed.2=${seed[1]} -p seed.3=${seed[2]} -p seed.4=${seed[3]} -p seed.5=${seed[4]} -p seed.6=${seed[5]} -p seed.7=${seed[6]}
cp out.stat ec/Project4P82/stat/bigNoADF4/run_${i}.stat


done