Redis works:
(2.1) Setup password
    C:\Program Files\Redis\conf\redis.conf: uncomment this line "requirepass foobared"
    And, restart windows service: "Redis Server"
        $ redis-cli
        redis 127.0.0.1:6379> AUTH foobared
        OK
        redis 127.0.0.1:6379> get name
        "Bob"
(2.2) Configuration
    redis 127.0.0.1:6379> CONFIG GET *
         1) "dir"
         2) "C:\\Program Files\\Redis\\data"
         3) "dbfilename"
         4) "dump.rdb"
         5) "requirepass"
         6) "foobared"
         7) "masterauth"
         8) (nil)
         9) "maxmemory"
        10) "0"
        11) "maxmemory-policy"
        12) "volatile-lru"
        13) "maxmemory-samples"
        14) "3"
        15) "timeout"
        16) "0"
        17) "appendonly"
        18) "no"
        19) "no-appendfsync-on-rewrite"
        20) "no"
        21) "appendfsync"
        22) "everysec"
        23) "save"
        24) "900 1 300 10 60 10000"
        25) "auto-aof-rewrite-percentage"
        26) "100"
        27) "auto-aof-rewrite-min-size"
        28) "67108864"
        29) "slave-serve-stale-data"
        30) "yes"
        31) "hash-max-zipmap-entries"
        32) "512"
        33) "hash-max-zipmap-value"
        34) "64"
        35) "list-max-ziplist-entries"
        36) "512"
        37) "list-max-ziplist-value"
        38) "64"
        39) "set-max-intset-entries"
        40) "512"
        41) "zset-max-ziplist-entries"
        42) "128"
        43) "zset-max-ziplist-value"
        44) "64"
        45) "slowlog-log-slower-than"
        46) "10000"
        47) "slowlog-max-len"
        48) "1024"
        49) "loglevel"
        50) "verbose"
    redis 127.0.0.1:6379> config get loglevel
        1) "loglevel"
        2) "verbose"
    redis 127.0.0.1:6379> CONFIG SET loglevel "notice"
        OK
    redis 127.0.0.1:6379> CONFIG GET loglevel
        1) "loglevel"
        2) "notice"

(2.3) Data Types -- Redis supports 5 types of data types
    2.3.1 String
        redis 127.0.0.1:6379> SET name "v.10"
            OK
        redis 127.0.0.1:6379> GET name
            "v.10"
    2.3.2 Hashes
        redis 127.0.0.1:6379> HMSET user:1 username tutorialspointN password tutorialsP
            OK
        redis 127.0.0.1:6379> HGETALL user:1
            1) "username"
            2) "tutorialspointN"
            3) "password"
            4) "tutorialsP"
        redis 127.0.0.1:6379> HGET user:1 username
            "tutorialspointN"
    2.3.3 Lists -- Redis Lists are simply lists of strings, sorted by insertion order.
        redis 127.0.0.1:6379> lpush tutoriallist redis
            (integer) 1
        redis 127.0.0.1:6379> lpush tutoriallist mongodb
            (integer) 2
        redis 127.0.0.1:6379> lpush tutoriallist rabitmq
            (integer) 3
        redis 127.0.0.1:6379> lrange tutoriallist 0 10
            1) "rabitmq"
            2) "mongodb"
            3) "redis"
    2.3.4 Sets -- Redis Sets are an unordered collection of strings.
        redis 127.0.0.1:6379> del tutoriallist
            (integer) 1
        redis 127.0.0.1:6379> sadd tutoriallist redis
            (integer) 1
        redis 127.0.0.1:6379> sadd tutoriallist mongodb
            (integer) 1
        redis 127.0.0.1:6379> sadd tutoriallist rabitmq
            (integer) 1
        redis 127.0.0.1:6379> sadd tutoriallist rabitmq
            (integer) 0
        redis 127.0.0.1:6379> smembers tutoriallist
            1) "redis"
            2) "rabitmq"
            3) "mongodb"
    2.3.5 Sorted Sets
        redis 127.0.0.1:6379> zadd tutoriallist 0 redis
            (integer) 1
        redis 127.0.0.1:6379> zadd tutoriallist 3 mongodb
            (integer) 1
        redis 127.0.0.1:6379> zadd tutoriallist 1 rabitmq
            (integer) 1
        redis 127.0.0.1:6379> ZRANGEBYSCORE tutoriallist 0 1000
            1) "redis"
            2) "rabitmq"
            3) "mongodb"
        redis 127.0.0.1:6379> zadd tutoriallist 2 MSMQ
            (integer) 1
        redis 127.0.0.1:6379> ZRANGEBYSCORE tutoriallist 0 1000
            1) "redis"
            2) "rabitmq"
            3) "MSMQ"
            4) "mongodb"
            
(2.4) Commands
    C:\Program Files\Redis>redis-cli -h 127.0.0.1 -p 6379 -a "foobared"
    redis 127.0.0.1:6379> del tutoriallist
        (integer) 1
    redis 127.0.0.1:6379> zadd tutoriallist 2 MSMQ
        (integer) 1
    redis 127.0.0.1:6379> ZRANGEBYSCORE tutoriallist 0 1000
        1) "MSMQ"
    redis 127.0.0.1:6379> ZRANGE tutoriallist 0 10 WITHSCORES
        1) "MSMQ"
        2) "2"
        
(2.5) Start service
    Open one prompt at:
        C:\Program Files\Redis>redis-server.exe redis.windows.conf
    And open another prompt at:
        C:\Program Files\Redis>redis-cli -h 127.0.0.1 -p 6379 -a "foobared"

(2.6) HyperLogLog
    127.0.0.1:6379> PFADD tutorials "redis"
        (integer) 1
    127.0.0.1:6379> PFADD tutorials "mongodb"
        (integer) 1
    127.0.0.1:6379> PFADD tutorials "mysql"
        (integer) 1
    127.0.0.1:6379> PFCOUNT tutorials
        (integer) 3

(2.7) Publish Subscribe
    One prompt:
        127.0.0.1:6379> SUBSCRIBE redisChat
        Reading messages... (press Ctrl-C to quit)
            1) "subscribe"
            2) "redisChat"
            3) (integer) 1
            1) "message"
            2) "redisChat"
            3) "Redis is a great caching technique"
            1) "message"
            2) "redisChat"
            3) "Learn redis by tutorials point"
    Anther prompt:
        127.0.0.1:6379> PUBLISH redisChat "Redis is a great caching technique"
            (integer) 1
        127.0.0.1:6379> PUBLISH redisChat "Learn redis by tutorials point"
            (integer) 1

(2.8) Transactions
    127.0.0.1:6379> get tutorial
        (nil)
    127.0.0.1:6379> SET tutorial redis3
        OK
    127.0.0.1:6379> get tutorial
        "redis3"
    127.0.0.1:6379> MULTI
        OK
    127.0.0.1:6379> SET tutorial redis4
        QUEUED
    127.0.0.1:6379> DISCARD
        OK
    127.0.0.1:6379> get tutorial
        "redis3"
    127.0.0.1:6379> MULTI
        OK
    127.0.0.1:6379> SET tutorial redis4
        QUEUED
    127.0.0.1:6379> exec
        1) OK
    127.0.0.1:6379> get tutorial
        "redis4"

(2.9) Server
    127.0.0.1:6379> info
        # Server
        redis_version:3.0.504
        redis_git_sha1:00000000
        redis_git_dirty:0
        redis_build_id:a4f7a6e86f2d60b3
        redis_mode:standalone
        os:Windows
        arch_bits:64
        multiplexing_api:WinSock_IOCP
        process_id:4356
        run_id:93607fa64ea8727ef11efabdf2fdead74ae70587
        tcp_port:6379
        uptime_in_seconds:523
        uptime_in_days:0
        hz:10
        lru_clock:10467027
        config_file:C:\Program Files\Redis\redis.windows.conf 
        # Clients
        connected_clients:1

(2.10) 
    In "C:\Program Files\Redis\redis.windows.conf", update below:
        #dir ./
        dir "C:/tfs/playzone/Python"
    To restore Redis data, move Redis backup file (dump.rdb) into your Redis directory and start the server. 
    To get your Redis directory, use CONFIG command of Redis as shown below.
        127.0.0.1:6379> SAVE
            OK
        127.0.0.1:6379> CONFIG get dir
            1) "dir"
            2) "C:\\tfs\\playzone\\Python"
    To create Redis backup, an alternate command BGSAVE is also available. 
    This command will start the backup process and run this in the background   
        127.0.0.1:6379> BGSAVE 
    
(2.11) Benchmarks
    c:\Program Files\Redis>redis-benchmark -n 100000
    c:\Program Files\Redis>redis-benchmark -h 127.0.0.1 -p 6379 -t set,lpush -n 100000 -q
        SET: 20580.37 requests per second
        LPUSH: 24588.15 requests per second
    
(2.12) Clients
    127.0.0.1:6379> config get maxclients
        1) "maxclients"
        2) "10000"
    
    
  https://www.tutorialspoint.com/redis/redis_hyperloglog.htm      
        
        
        
        
        
        
        
        
        
        
        