#!/bin/bash

declare -a standardBest
declare -a standardAverage
stringOutput=""
declare tPositive
declare fPositive
declare tNegative
declare fNegative
declare size
declare totalHits

for dir in ec/Project4P82/stat/*; do
  tPositive=0
  tNegative=0
  fNegative=0
  fPositive=0
  stringOutput=""
  for file in "$dir"/*.stat; do
    while IFS= read -r line; do

      if [[ "$line" =~ (Best Individual of Run) ]]; then
        continue
      fi

      if [[ "$line" =~ (Generation: [0-9]{1,2}) ]]; then
        generation=${BASH_REMATCH[0]}
        generation=${generation/Generation: }
      fi

      if [[ "$line" =~ ^(Standardized Fitness.*)$ ]]; then
        standard=${BASH_REMATCH[0]/Standardized Fitness}
        read -ra standardArray <<< "$standard"
        if [[ ${standardBest[generation]} ]]; then
          formula=${standardBest[generation]+${standardArray[0]}}
          standardBest[generation]=$(awk "BEGIN {print $formula}")
        else
          standardBest[generation]=${standardArray[0]}
        fi
        if [[ ${standardAverage[generation]} ]]; then
          formula=${standardAverage[generation]+${standardArray[1]}}
          standardAverage[generation]=$(awk "BEGIN {print $formula}")
        else
          standardAverage[generation]=${standardArray[1]}
        fi
      fi
      if [[ "$line" =~ ^(True Positive: [0-9]{1,5})$ ]]; then
        pp=${BASH_REMATCH[0]/True Positive: }
        if [[ ${tPositive} ]]; then
          formula=${tPositive}+${pp}
          tPositive=$(awk "BEGIN {print $formula}")
        else
          tPositive=${pp}
        fi
      fi
      if [[ "$line" =~ ^(True Negative: [0-9]{1,5})$ ]]; then
        np=${BASH_REMATCH[0]/True Negative: }
        if [[ ${tNegative} ]]; then
          formula=${tNegative}+${np}
          tNegative=$(awk "BEGIN {print $formula}")
        else
          tNegative=${np}
        fi
      fi
      if [[ "$line" =~ ^(False Positive: [0-9]{1,5})$ ]]; then
        fpp=${BASH_REMATCH[0]/False Positive: }
        if [[ ${fPositive} ]]; then
          formula=${fPositive}+${fpp}
          fPositive=$(awk "BEGIN {print $formula}")
        else
          fPositive=${fpp}
        fi
      fi
      if [[ "$line" =~ ^(False Negative: [0-9]{1,5})$ ]]; then
        fnp=${BASH_REMATCH[0]/False Negative: }
        if [[ ${fNegative} ]]; then
          formula=${fNegative}+${fnp}
          fNegative=$(awk "BEGIN {print $formula}")
        else
          fNegative=${fnp}
        fi
      fi
      if [[ "$line" =~ ^(Testing set size: [0-9]{1,3})$ ]]; then
        s=${BASH_REMATCH[0]/Testing set size: }
        if [[ ${size} ]]; then
          echo "already something here"
        else
          size=${s}
        fi
      fi
      if [[ "$line" =~ ^(Test Set Total Hits: [0-9]{1,5})$ ]]; then
        h=${BASH_REMATCH[0]/Test Set Total Hits: }
        if [[ ! ${totalHits} ]]; then
          totalHits=${h}
        fi
      fi
    done < "$file"
  done
  formula="${tPositive}/10"
  tPositive=$(awk "BEGIN {print ${formula}}")
  formula="${tNegative}/10"
  tNegative=$(awk "BEGIN {print ${formula}}")
  formula="${fPositive}/10"
  fPositive=$(awk "BEGIN {print ${formula}}")
  formula="${fNegative}/10"
  fNegative=$(awk "BEGIN {print ${formula}}")

  for i in "${!standardAverage[@]}"; do
    standardAverage[i]=$(awk "BEGIN {print ${standardAverage[i]}/10}")
    standardBest[i]=$(awk "BEGIN {print ${standardBest[i]}/10}")

    if [[ ${i} == 0 ]]; then
      stringOutput+="${standardAverage[i]}, ${standardBest[i]}, $tPositive, $fPositive, $tNegative, $fNegative, $totalHits\n"
    else
      stringOutput+="${standardAverage[i]}, ${standardBest[i]},\n"
    fi
  done
  stringOutput=${stringOutput::-3}
  echo -e "${stringOutput}" > "$dir"/excel.txt
done