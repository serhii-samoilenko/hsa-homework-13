# Queues demo demo report

For each Redis persistence mode, another Redis Docker container will be started.

---

## Redis Pub-Sub in different persistence modes

### Redis Pub-Sub NoP

Docker container: `redis:7-alpine`, command: `[redis-server, --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `5865 ops in 10.028s - 584.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `4828 ops in 10.042s - 480.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1331 ops in 10.1s - 131.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `280 ops in 10.933s - 25.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `3259 ops in 10.182s - 320.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `2690 ops in 10.125s - 265.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1971 ops in 10.398s - 189.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `346 ops in 11.168s - 31.0 ops/sec, 3 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `6966 ops in 10.236s - 680.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `4802 ops in 10.15s - 473.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `1845 ops in 10.294s - 179.2 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `147 ops in 16.241s - 9.05 ops/sec, 268 fails`

### Redis Pub-Sub AOF

Docker container: `redis:7-alpine`, command: `[redis-server, --appendonly, yes]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `5095 ops in 10.173s - 500.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `3709 ops in 10.167s - 364.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1454 ops in 10.155s - 143.2 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `302 ops in 10.334s - 29.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `4481 ops in 10.189s - 439.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `2266 ops in 10.165s - 222.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1046 ops in 10.42s - 100.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `244 ops in 12.549s - 19.4 ops/sec, 61 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `3482 ops in 10.155s - 342.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `4454 ops in 10.168s - 438.0 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `1761 ops in 10.371s - 169.8 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `272 ops in 11.926s - 22.8 ops/sec, 209 fails`

### Redis Pub-Sub RDB

Docker container: `redis:7-alpine`, command: `[redis-server, --save, 5 1000, --save, 1 100]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `10295 ops in 10.076s - 1021.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `4730 ops in 10.027s - 471.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1839 ops in 10.141s - 181.3 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `358 ops in 10.211s - 35.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `8976 ops in 10.057s - 892.5 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `5767 ops in 10.2s - 565.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `2295 ops in 10.229s - 224.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `402 ops in 11.487s - 35.0 ops/sec, 2 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `10706 ops in 10.032s - 1067.2 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `7161 ops in 10.127s - 707.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `2419 ops in 10.356s - 233.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `405 ops in 11.548s - 35.1 ops/sec, 164 fails`

## Redis Rpush-Lpop in different persistence modes

### Redis Rpush-Lpop NoP

Docker container: `redis:7-alpine`, command: `[redis-server, --appendonly, no]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `8459 ops in 11.529s - 733.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `4328 ops in 11.832s - 365.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1409 ops in 11.785s - 119.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `259 ops in 11.884s - 21.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `8896 ops in 14.42s - 616.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `5573 ops in 14.304s - 389.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1771 ops in 13.342s - 132.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `353 ops in 16.542s - 21.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `8035 ops in 14.606s - 550.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `5080 ops in 14.702s - 345.5 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `1728 ops in 14.442s - 119.7 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `405 ops in 17.78s - 22.8 ops/sec, 54 fails`

### Redis Rpush-Lpop AOF

Docker container: `redis:7-alpine`, command: `[redis-server, --appendonly, yes]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `8472 ops in 11.724s - 722.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `3641 ops in 12.57s - 289.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1213 ops in 11.963s - 101.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `228 ops in 11.57s - 19.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `12206 ops in 12.837s - 950.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `8799 ops in 13.128s - 670.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `2427 ops in 12.531s - 193.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `368 ops in 15.09s - 24.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `8299 ops in 13.94s - 595.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `5063 ops in 14.073s - 359.8 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `1550 ops in 14.341s - 108.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `346 ops in 17.501s - 19.8 ops/sec, 44 fails`

### Redis Rpush-Lpop RDB

Docker container: `redis:7-alpine`, command: `[redis-server, --save, 5 1000, --save, 1 100]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `7767 ops in 12.524s - 620.2 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `4154 ops in 11.69s - 355.3 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1283 ops in 11.536s - 111.2 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `248 ops in 12.181s - 20.4 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `8263 ops in 13.782s - 599.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `4944 ops in 13.443s - 367.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1744 ops in 13.876s - 125.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `297 ops in 14.428s - 20.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `10505 ops in 15.379s - 683.1 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `6358 ops in 12.659s - 502.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `2653 ops in 14.506s - 182.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `315 ops in 14.019s - 22.5 ops/sec, 100 fails`

## BeansTalk without persistence, with immediate flush, and with 1s flush interval

### Beanstalk NoP

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-F, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `17021 ops in 13.673s - 1244.9 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `8471 ops in 12.609s - 671.8 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `2126 ops in 12.013s - 177.0 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `294 ops in 12.708s - 23.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `24337 ops in 13.133s - 1853.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `15942 ops in 13.101s - 1216.9 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `4287 ops in 12.246s - 350.1 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `417 ops in 11.171s - 37.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `24331 ops in 13.453s - 1808.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `10647 ops in 13.907s - 765.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `2716 ops in 12.11s - 224.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `528 ops in 16.522s - 32.0 ops/sec, 19 fails`

### Beanstalk 0s

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-b, /data, -f, 0, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `1991 ops in 12.016s - 165.7 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `1606 ops in 12.034s - 133.5 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `1209 ops in 12.341s - 98.0 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `319 ops in 11.542s - 27.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `2663 ops in 12.504s - 213.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `2051 ops in 12.12s - 169.2 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `1768 ops in 13.228s - 133.7 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `363 ops in 13.264s - 27.4 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `3203 ops in 14.519s - 220.6 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `2444 ops in 12.595s - 194.0 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `1804 ops in 13.332s - 135.3 ops/sec, 0 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `519 ops in 19.829s - 26.2 ops/sec, 13 fails`

### Beanstalk 1s

Docker container: `uretgec/beanstalkd-alpine:latest`, command: `[-b, /data, -f, 1000, -z, 10485760]`

#### Pool size: `10`, message size: `1-2Kb`

Result: `10761 ops in 14.574s - 738.4 ops/sec, 0 fails`

#### Pool size: `10`, message size: `10-20Kb`

Result: `5989 ops in 13.862s - 432.0 ops/sec, 0 fails`

#### Pool size: `10`, message size: `50-100Kb`

Result: `2035 ops in 12.361s - 164.6 ops/sec, 0 fails`

#### Pool size: `10`, message size: `500Kb-1Mb`

Result: `242 ops in 11.217s - 21.6 ops/sec, 0 fails`

#### Pool size: `50`, message size: `1-2Kb`

Result: `10765 ops in 12.985s - 829.0 ops/sec, 0 fails`

#### Pool size: `50`, message size: `10-20Kb`

Result: `10478 ops in 13.446s - 779.3 ops/sec, 0 fails`

#### Pool size: `50`, message size: `50-100Kb`

Result: `2798 ops in 11.918s - 234.8 ops/sec, 0 fails`

#### Pool size: `50`, message size: `500Kb-1Mb`

Result: `512 ops in 13.566s - 37.7 ops/sec, 0 fails`

#### Pool size: `100`, message size: `1-2Kb`

Result: `22206 ops in 13.242s - 1676.9 ops/sec, 0 fails`

#### Pool size: `100`, message size: `10-20Kb`

Result: `9391 ops in 12.957s - 724.8 ops/sec, 0 fails`

#### Pool size: `100`, message size: `50-100Kb`

Result: `3353 ops in 13.183s - 254.3 ops/sec, 5 fails`

#### Pool size: `100`, message size: `500Kb-1Mb`

Result: `560 ops in 18.252s - 30.7 ops/sec, 25 fails`

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
    <td>584.9</td>
    <td>500.8</td>
    <td>1021.7</td>
    <td>733.7</td>
    <td>722.6</td>
    <td>620.2</td>
    <td>1244.9</td>
    <td>165.7</td>
    <td>738.4</td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 10-20Kb</th>
    <td>480.8</td>
    <td>364.8</td>
    <td>471.7</td>
    <td>365.8</td>
    <td>289.7</td>
    <td>355.3</td>
    <td>671.8</td>
    <td>133.5</td>
    <td>432.0</td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 50-100Kb</th>
    <td>131.8</td>
    <td>143.2</td>
    <td>181.3</td>
    <td>119.6</td>
    <td>101.4</td>
    <td>111.2</td>
    <td>177.0</td>
    <td>98.0</td>
    <td>164.6</td>
  </tr>
  <tr>
    <th>Pool: 10, payload: 500Kb-1Mb</th>
    <td>25.6</td>
    <td>29.2</td>
    <td>35.1</td>
    <td>21.8</td>
    <td>19.7</td>
    <td>20.4</td>
    <td>23.1</td>
    <td>27.6</td>
    <td>21.6</td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 1-2Kb</th>
    <td>320.1</td>
    <td>439.8</td>
    <td>892.5</td>
    <td>616.9</td>
    <td>950.8</td>
    <td>599.6</td>
    <td>1853.1</td>
    <td>213.0</td>
    <td>829.0</td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 10-20Kb</th>
    <td>265.7</td>
    <td>222.9</td>
    <td>565.4</td>
    <td>389.6</td>
    <td>670.2</td>
    <td>367.8</td>
    <td>1216.9</td>
    <td>169.2</td>
    <td>779.3</td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 50-100Kb</th>
    <td>189.6</td>
    <td>100.4</td>
    <td>224.4</td>
    <td>132.7</td>
    <td>193.7</td>
    <td>125.7</td>
    <td>350.1</td>
    <td>133.7</td>
    <td>234.8</td>
  </tr>
  <tr>
    <th>Pool: 50, payload: 500Kb-1Mb</th>
    <td>31.0</td>
    <td>19.4</td>
    <td>35.0</td>
    <td>21.3</td>
    <td>24.4</td>
    <td>20.6</td>
    <td>37.3</td>
    <td>27.4</td>
    <td>37.7</td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 1-2Kb</th>
    <td>680.5</td>
    <td>342.9</td>
    <td>1067.2</td>
    <td>550.1</td>
    <td>595.3</td>
    <td>683.1</td>
    <td>1808.6</td>
    <td>220.6</td>
    <td>1676.9</td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 10-20Kb</th>
    <td>473.1</td>
    <td>438.0</td>
    <td>707.1</td>
    <td>345.5</td>
    <td>359.8</td>
    <td>502.3</td>
    <td>765.6</td>
    <td>194.0</td>
    <td>724.8</td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 50-100Kb</th>
    <td>179.2</td>
    <td>169.8</td>
    <td>233.6</td>
    <td>119.7</td>
    <td>108.1</td>
    <td>182.9</td>
    <td>224.3</td>
    <td>135.3</td>
    <td>254.3</td>
  </tr>
  <tr>
    <th>Pool: 100, payload: 500Kb-1Mb</th>
    <td>9.05</td>
    <td>22.8</td>
    <td>35.1</td>
    <td>22.8</td>
    <td>19.8</td>
    <td>22.5</td>
    <td>32.0</td>
    <td>26.2</td>
    <td>30.7</td>
  </tr>
</table>
