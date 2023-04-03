# Queues demo demo report

For each Redis persistence mode, another Redis Docker container will be started.

---

## Redis Pub-Sub in different persistence modes

### Redis Pub-Sub NoP

Docker container: `redis:7-alpine`, command: `[redis-server, --save, , --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `102340 ops in 2m 0.08s - 852.3 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `67293 ops in 2m 0.099s - 560.3 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `22209 ops in 2m 0.127s - 184.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `3983 ops in 2m 0.278s - 33.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `99837 ops in 2m 0.105s - 831.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `68733 ops in 2m 0.042s - 572.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `25254 ops in 2m 0.518s - 209.5 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `3379 ops in 2m 1.344s - 27.8 ops/sec, 65 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `95737 ops in 2m 0.294s - 795.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `67702 ops in 2m 0.137s - 563.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `24986 ops in 2m 0.427s - 207.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `3473 ops in 2m 2.466s - 28.4 ops/sec, 2144 fails`

### Redis Pub-Sub AOF

Docker container: `redis:7-alpine`, command: `[redis-server, --save, , --appendonly, yes]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `96716 ops in 2m 0.045s - 805.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `54134 ops in 2m 0.116s - 450.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `20615 ops in 2m 0.067s - 171.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `3201 ops in 2m 0.325s - 26.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `92296 ops in 2m 0.179s - 768.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `65372 ops in 2m 0.138s - 544.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `25374 ops in 2m 0.256s - 211.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `3408 ops in 2m 2.188s - 27.9 ops/sec, 111 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `97537 ops in 2m 0.17s - 811.7 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `70990 ops in 2m 0.134s - 590.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `25305 ops in 2m 0.538s - 209.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `4353 ops in 2m 2.682s - 35.5 ops/sec, 1389 fails`

### Redis Pub-Sub RDB

Docker container: `redis:7-alpine`, command: `[redis-server, --save, 5 1000, --save, 1 100, --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `106143 ops in 2m 0.116s - 883.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `57374 ops in 2m 0.156s - 477.5 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `20129 ops in 2m 0.127s - 167.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `3136 ops in 2m 0.263s - 26.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `89556 ops in 2m 0.105s - 745.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `63427 ops in 2m 0.114s - 528.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `24895 ops in 2m 0.276s - 207.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `4215 ops in 2m 1.983s - 34.6 ops/sec, 39 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `109448 ops in 2m 0.168s - 910.8 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `68143 ops in 2m 0.12s - 567.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `24235 ops in 2m 0.436s - 201.2 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `3458 ops in 2m 1.353s - 28.5 ops/sec, 1950 fails`

## Redis Rpush-Lpop in different persistence modes

### Redis Rpush-Lpop NoP

Docker container: `redis:7-alpine`, command: `[redis-server, --save, , --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `88897 ops in 2m 12.678s - 670.0 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `49448 ops in 2m 13.079s - 371.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `16014 ops in 2m 8.945s - 124.2 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `2004 ops in 2m 5.228s - 16.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `145585 ops in 2m 34.648s - 941.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `53854 ops in 2m 29.947s - 359.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `18962 ops in 2m 17.761s - 137.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `3232 ops in 2m 23.204s - 22.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `103451 ops in 2m 27.939s - 699.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `58590 ops in 2m 24.308s - 406.0 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `19452 ops in 2m 24.038s - 135.0 ops/sec, 18 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `3066 ops in 2m 25.861s - 21.0 ops/sec, 868 fails`

### Redis Rpush-Lpop AOF

Docker container: `redis:7-alpine`, command: `[redis-server, --save, , --appendonly, yes]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `107103 ops in 2m 11.132s - 816.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `82580 ops in 2m 12.94s - 621.2 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `27233 ops in 2m 6.924s - 214.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `3288 ops in 2m 1.911s - 27.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `166745 ops in 2m 25.911s - 1142.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `104649 ops in 2m 26.321s - 715.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `26955 ops in 3m 1.358s - 148.6 ops/sec, 40 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `1546 ops in 2m 6.832s - 12.2 ops/sec, 426 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `113706 ops in 2m 33.467s - 740.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `74260 ops in 2m 29.038s - 498.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `20640 ops in 2m 36.464s - 131.9 ops/sec, 518 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `1333 ops in 3m 10.975s - 6.98 ops/sec, 3772 fails`

### Redis Rpush-Lpop RDB

Docker container: `redis:7-alpine`, command: `[redis-server, --save, 5 1000, --save, 1 100, --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `117329 ops in 2m 12.484s - 885.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `56497 ops in 2m 22.655s - 396.0 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `21474 ops in 2m 4.914s - 171.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `2622 ops in 2m 2.887s - 21.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `127282 ops in 2m 24.493s - 880.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `74461 ops in 2m 28.111s - 502.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `25892 ops in 2m 23.829s - 180.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `2816 ops in 2m 15.178s - 20.8 ops/sec, 46 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `117542 ops in 2m 26.341s - 803.2 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `19385 ops in 2m 42.922s - 119.0 ops/sec, 125 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `10007 ops in 2m 11.102s - 76.3 ops/sec, 63 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `1385 ops in 2m 5.87s - 11.0 ops/sec, 1759 fails`

## BeansTalk without persistence, with immediate flush, and with 1s flush interval

### Beanstalk NoP

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-F, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `75495 ops in 2m 32.861s - 493.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `74442 ops in 2m 34.825s - 480.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `23142 ops in 2m 17.655s - 168.1 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `2915 ops in 2m 3.697s - 23.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `194148 ops in 2m 26.7s - 1323.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `107288 ops in 2m 32.281s - 704.5 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `25368 ops in 2m 15.479s - 187.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `3416 ops in 2m 8.655s - 26.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `208047 ops in 2m 22.273s - 1462.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `110757 ops in 2m 32.692s - 725.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `29363 ops in 2m 17.47s - 213.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `3602 ops in 2m 11.613s - 27.4 ops/sec, 26 fails`

### Beanstalk 0s

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-b, /data, -f, 0, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `19410 ops in 2m 11.46s - 147.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `17357 ops in 2m 10.098s - 133.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `12382 ops in 2m 9.751s - 95.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `3516 ops in 2m 5.136s - 28.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `27993 ops in 2m 10.608s - 214.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `19884 ops in 2m 13.082s - 149.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `13910 ops in 2m 17.898s - 100.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `3146 ops in 2m 11.304s - 24.0 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `28425 ops in 2m 9.554s - 219.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `35933 ops in 2m 19.505s - 257.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `17098 ops in 2m 22.218s - 120.2 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `3194 ops in 2m 16.284s - 23.4 ops/sec, 0 fails`

### Beanstalk 1s

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-b, /data, -f, 1000, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `74925 ops in 2m 32.033s - 492.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `67902 ops in 2m 34.574s - 439.3 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `21544 ops in 2m 15.134s - 159.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `2831 ops in 2m 3.762s - 22.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `174055 ops in 2m 24.445s - 1205.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `92240 ops in 2m 29.708s - 616.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `25973 ops in 2m 16.948s - 189.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `3419 ops in 2m 6.954s - 26.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `182667 ops in 2m 22.078s - 1285.7 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `103725 ops in 2m 30.429s - 689.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `31503 ops in 2m 16.736s - 230.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `4170 ops in 2m 14.453s - 31.0 ops/sec, 29 fails`

<table>
  <tr>
    <th><i>Ops/sec</i></th>
    <th>Redis<br/>Pub-Sub<br/>NoP</th>
    <th>Redis<br/>Pub-Sub<br/>AOF</th>
    <th>Redis<br/>Pub-Sub<br/>RDB</th>
    <th>Redis<br/>Rpush-Lpop<br/>NoP</th>
    <th>Redis<br/>Rpush-Lpop<br/>AOF</th>
    <th>Redis<br/>Rpush-Lpop<br/>RDB</th>
    <th>Beanstalk<br/>NoP</th>
    <th>Beanstalk<br/>0s</th>
    <th>Beanstalk<br/>1s</th>
  </tr>
  <tr>
    <th>Pool: 10, payload: 1-2Kb</th>
    <td>852.3 </td>
    <td>805.7 </td>
    <td>883.7 </td>
    <td>670.0 </td>
    <td>621.4 </td>
    <td>680.0 </td>
    <td>493.9 </td>
    <td>147.6 </td>
    <td>492.8 </td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 10-20Kb</th>
    <td>560.3 </td>
    <td>450.7 </td>
    <td>477.5 </td>
    <td>371.6 </td>
    <td>345.9 </td>
    <td>322.5 </td>
    <td>480.8 </td>
    <td>133.4 </td>
    <td>439.3 </td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 50-100Kb</th>
    <td>184.9 </td>
    <td>171.7 </td>
    <td>167.6 </td>
    <td>124.2 </td>
    <td>98.8 </td>
    <td>111.5 </td>
    <td>168.1 </td>
    <td>95.4 </td>
    <td>159.4 </td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 500Kb-1Mb</th>
    <td>33.1 </td>
    <td>26.6 </td>
    <td>26.1 </td>
    <td>16.0 </td>
    <td>12.3 <i>F0.6%</i></td>
    <td>19.9 </td>
    <td>23.6 </td>
    <td>28.1 </td>
    <td>22.9 </td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 1-2Kb</th>
    <td>831.2 </td>
    <td>768.0 </td>
    <td>745.6 </td>
    <td>941.4 </td>
    <td>550.8 </td>
    <td>635.7 </td>
    <td>1323.4 </td>
    <td>214.3 </td>
    <td>1205.0 </td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 10-20Kb</th>
    <td>572.6 </td>
    <td>544.1 </td>
    <td>528.1 </td>
    <td>359.2 </td>
    <td>259.0 </td>
    <td>333.7 </td>
    <td>704.5 </td>
    <td>149.4 </td>
    <td>616.1 </td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 50-100Kb</th>
    <td>209.5 </td>
    <td>211.0 </td>
    <td>207.0 </td>
    <td>137.6 </td>
    <td>97.6 </td>
    <td>100.7 </td>
    <td>187.2 </td>
    <td>100.9 </td>
    <td>189.7 </td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 500Kb-1Mb</th>
    <td>27.8 <i>F1.9%</i></td>
    <td>27.9 <i>F3.3%</i></td>
    <td>34.6 <i>F0.9%</i></td>
    <td>22.6 </td>
    <td>9.2 <i>F27.6%</i></td>
    <td>13.8 <i>F1.6%</i></td>
    <td>26.6 </td>
    <td>24.0 </td>
    <td>26.9 </td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 1-2Kb</th>
    <td>795.9 </td>
    <td>811.7 </td>
    <td>910.8 </td>
    <td>699.3 </td>
    <td>475.5 </td>
    <td>545.6 </td>
    <td>1462.3 </td>
    <td>219.4 </td>
    <td>1285.7 </td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 10-20Kb</th>
    <td>563.5 </td>
    <td>590.9 </td>
    <td>567.3 </td>
    <td>406.0 </td>
    <td>142.1 <i>F18.2%</i></td>
    <td>210.8 <i>F13.4%</i></td>
    <td>725.4 </td>
    <td>257.6 </td>
    <td>689.5 </td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 50-100Kb</th>
    <td>207.5 </td>
    <td>209.9 </td>
    <td>201.2 </td>
    <td>135.0 <i>F0.1%</i></td>
    <td>66.9 <i>F2.5%</i></td>
    <td>76.3 <i>F0.6%</i></td>
    <td>213.6 </td>
    <td>120.2 </td>
    <td>230.4 </td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 500Kb-1Mb</th>
    <td>28.4 <i>F61.7%</i></td>
    <td>35.5 <i>F31.9%</i></td>
    <td>28.5 <i>F56.4%</i></td>
    <td>21.0 <i>F28.3%</i></td>
    <td>6.98 <i>F283.0%</i></td>
    <td>11.0 <i>F127.0%</i></td>
    <td>27.4 <i>F0.7%</i></td>
    <td>23.4 </td>
    <td>31.0 <i>F0.7%</i></td>
  </tr>
</table>
