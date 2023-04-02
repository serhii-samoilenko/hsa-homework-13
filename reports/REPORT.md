# Queues demo demo report

For each Redis persistence mode, another Redis Docker container will be started.

---

## Redis Pub-Sub in different persistence modes

### Redis Pub-Sub NoP

Docker container: `redis:7-alpine`, command: `[redis-server, --save, , --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `6497 ops in 10.099s - 643.3 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `5345 ops in 10.135s - 527.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1893 ops in 10.197s - 185.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `326 ops in 10.345s - 31.5 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `8874 ops in 10.133s - 875.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `6182 ops in 10.091s - 612.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `2415 ops in 10.166s - 237.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `399 ops in 11.43s - 34.9 ops/sec, 5 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `10270 ops in 10.146s - 1012.2 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `7003 ops in 10.151s - 689.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `2432 ops in 10.405s - 233.7 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `384 ops in 10.723s - 35.8 ops/sec, 94 fails`

### Redis Pub-Sub AOF

Docker container: `redis:7-alpine`, command: `[redis-server, --save, , --appendonly, yes]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `10334 ops in 10.088s - 1024.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `6246 ops in 10.157s - 614.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `2138 ops in 10.072s - 212.3 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `390 ops in 10.32s - 37.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `9349 ops in 10.126s - 923.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `6492 ops in 10.043s - 646.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `2629 ops in 10.298s - 255.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `324 ops in 10.669s - 30.4 ops/sec, 1 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `9324 ops in 10.287s - 906.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `6710 ops in 10.158s - 660.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `2536 ops in 10.249s - 247.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `388 ops in 10.922s - 35.5 ops/sec, 130 fails`

### Redis Pub-Sub RDB

Docker container: `redis:7-alpine`, command: `[redis-server, --save, 5 1000, --save, 1 100, --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `9921 ops in 10.031s - 989.0 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `6447 ops in 10.009s - 644.1 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `2158 ops in 10.122s - 213.2 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `384 ops in 10.339s - 37.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `9776 ops in 10.124s - 965.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `6591 ops in 10.079s - 653.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `2361 ops in 10.274s - 229.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `436 ops in 11.759s - 37.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `10360 ops in 10.029s - 1033.0 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `7032 ops in 10.126s - 694.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `2326 ops in 10.485s - 221.8 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `421 ops in 11.922s - 35.3 ops/sec, 74 fails`

## Redis Rpush-Lpop in different persistence modes

### Redis Rpush-Lpop NoP

Docker container: `redis:7-alpine`, command: `[redis-server, --save, , --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `8901 ops in 11.786s - 755.2 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `4957 ops in 11.296s - 438.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1551 ops in 11.169s - 138.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `253 ops in 11.472s - 22.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `8693 ops in 13.313s - 653.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `5257 ops in 13.483s - 389.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1869 ops in 13.126s - 142.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `359 ops in 15.964s - 22.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `9345 ops in 14.911s - 626.7 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `5773 ops in 14.52s - 397.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `2054 ops in 14.678s - 139.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `438 ops in 1m 12.069s - 6.08 ops/sec, 63 fails`

### Redis Rpush-Lpop AOF

Docker container: `redis:7-alpine`, command: `[redis-server, --save, , --appendonly, yes]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `8455 ops in 11.409s - 741.1 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `4503 ops in 11.489s - 391.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1346 ops in 11.377s - 118.3 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `221 ops in 11.378s - 19.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `8991 ops in 13.952s - 644.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `5208 ops in 13.62s - 382.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1620 ops in 13.699s - 118.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `342 ops in 18.613s - 18.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `9791 ops in 14.678s - 667.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `4546 ops in 13.902s - 327.0 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `1733 ops in 14.804s - 117.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `299 ops in 15.894s - 18.8 ops/sec, 74 fails`

### Redis Rpush-Lpop RDB

Docker container: `redis:7-alpine`, command: `[redis-server, --save, 5 1000, --save, 1 100, --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `8077 ops in 12.147s - 664.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `5030 ops in 11.54s - 435.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1524 ops in 11.122s - 137.0 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `237 ops in 11.461s - 20.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `9676 ops in 13.51s - 716.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `5405 ops in 13.473s - 401.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1785 ops in 13.819s - 129.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `352 ops in 16.382s - 21.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `9470 ops in 14.977s - 632.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `5762 ops in 14.796s - 389.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `1840 ops in 14.398s - 127.8 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `402 ops in 19.232s - 20.9 ops/sec, 150 fails`

## BeansTalk without persistence, with immediate flush, and with 1s flush interval

### Beanstalk NoP

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-F, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `10984 ops in 14.29s - 768.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `7399 ops in 13.554s - 545.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `2498 ops in 12.272s - 203.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `402 ops in 11.734s - 34.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `23639 ops in 13.849s - 1706.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `11912 ops in 14.336s - 830.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `3468 ops in 12.448s - 278.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `441 ops in 12.407s - 35.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `29580 ops in 13.547s - 2183.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `13220 ops in 14.589s - 906.2 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `3604 ops in 12.686s - 284.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `525 ops in 16.327s - 32.2 ops/sec, 18 fails`

### Beanstalk 0s

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-b, /data, -f, 0, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `2120 ops in 11.792s - 179.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `1981 ops in 11.687s - 169.5 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1338 ops in 11.723s - 114.1 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `329 ops in 11.551s - 28.5 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `2640 ops in 12.891s - 204.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `2200 ops in 12.354s - 178.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1881 ops in 12.741s - 147.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `431 ops in 13.571s - 31.8 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `4336 ops in 14.773s - 293.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `3106 ops in 14.282s - 217.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `161 ops in 28m 48.227s - 0.093 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `349 ops in 14.636s - 23.8 ops/sec, 13 fails`

### Beanstalk 1s

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-b, /data, -f, 1000, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `9407 ops in 14.074s - 668.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `5839 ops in 12.381s - 471.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `2234 ops in 12.738s - 175.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `342 ops in 11.524s - 29.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `22362 ops in 13.348s - 1675.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `9863 ops in 13.233s - 745.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `3221 ops in 12.196s - 264.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `435 ops in 13.667s - 31.8 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `21160 ops in 12.621s - 1676.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `11441 ops in 13.386s - 854.7 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `3170 ops in 12.658s - 250.4 ops/sec, 4 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `648 ops in 21.537s - 30.1 ops/sec, 21 fails`

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
    <td>643.3 </td>
    <td>1024.4 </td>
    <td>989.0 </td>
    <td>755.2 </td>
    <td>741.1 </td>
    <td>664.9 </td>
    <td>768.6 </td>
    <td>179.8 </td>
    <td>668.4 </td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 10-20Kb</th>
    <td>527.4 </td>
    <td>614.9 </td>
    <td>644.1 </td>
    <td>438.8 </td>
    <td>391.9 </td>
    <td>435.9 </td>
    <td>545.9 </td>
    <td>169.5 </td>
    <td>471.6 </td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 50-100Kb</th>
    <td>185.6 </td>
    <td>212.3 </td>
    <td>213.2 </td>
    <td>138.9 </td>
    <td>118.3 </td>
    <td>137.0 </td>
    <td>203.6 </td>
    <td>114.1 </td>
    <td>175.4 </td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 500Kb-1Mb</th>
    <td>31.5 </td>
    <td>37.8 </td>
    <td>37.1 </td>
    <td>22.1 </td>
    <td>19.4 </td>
    <td>20.7 </td>
    <td>34.3 </td>
    <td>28.5 </td>
    <td>29.7 </td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 1-2Kb</th>
    <td>875.8 </td>
    <td>923.3 </td>
    <td>965.6 </td>
    <td>653.0 </td>
    <td>644.4 </td>
    <td>716.2 </td>
    <td>1706.9 </td>
    <td>204.8 </td>
    <td>1675.3 </td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 10-20Kb</th>
    <td>612.6 </td>
    <td>646.4 </td>
    <td>653.9 </td>
    <td>389.9 </td>
    <td>382.4 </td>
    <td>401.2 </td>
    <td>830.9 </td>
    <td>178.1 </td>
    <td>745.3 </td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 50-100Kb</th>
    <td>237.6 </td>
    <td>255.3 </td>
    <td>229.8 </td>
    <td>142.4 </td>
    <td>118.3 </td>
    <td>129.2 </td>
    <td>278.6 </td>
    <td>147.6 </td>
    <td>264.1 </td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 500Kb-1Mb</th>
    <td>34.9 <i>F1.3%</i></td>
    <td>30.4 <i>F0.3%</i></td>
    <td>37.1 </td>
    <td>22.5 </td>
    <td>18.4 </td>
    <td>21.5 </td>
    <td>35.5 </td>
    <td>31.8 </td>
    <td>31.8 </td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 1-2Kb</th>
    <td>1012.2 </td>
    <td>906.4 </td>
    <td>1033.0 </td>
    <td>626.7 </td>
    <td>667.1 </td>
    <td>632.3 </td>
    <td>2183.5 </td>
    <td>293.5 </td>
    <td>1676.6 </td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 10-20Kb</th>
    <td>689.9 </td>
    <td>660.6 </td>
    <td>694.4 </td>
    <td>397.6 </td>
    <td>327.0 </td>
    <td>389.4 </td>
    <td>906.2 </td>
    <td>217.5 </td>
    <td>854.7 </td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 50-100Kb</th>
    <td>233.7 </td>
    <td>247.4 </td>
    <td>221.8 </td>
    <td>139.9 </td>
    <td>117.1 </td>
    <td>127.8 </td>
    <td>284.1 </td>
    <td>0.093 </td>
    <td>250.4 <i>F0.1%</i></td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 500Kb-1Mb</th>
    <td>35.8 <i>F24.5%</i></td>
    <td>35.5 <i>F33.5%</i></td>
    <td>35.3 <i>F17.6%</i></td>
    <td>6.08 <i>F14.4%</i></td>
    <td>18.8 <i>F24.7%</i></td>
    <td>20.9 <i>F37.3%</i></td>
    <td>32.2 <i>F3.4%</i></td>
    <td>23.8 <i>F3.7%</i></td>
    <td>30.1 <i>F3.2%</i></td>
  </tr>
</table>
