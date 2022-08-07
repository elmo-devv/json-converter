### Keystore File Migration Converter

#### 실행방법
1. jar 파일 빌드
2. jar 파일 실행
```
java -jar -DasIsFileDir=./asis -DtoBeFileDir=./tobe -DasIsFileName=asis_keys.json parser-0.0.1-SNAPSHOT.jar
```
3. Please select a service [1] converter [2] tester 중 1, 2 선택

---

ASIS 파일 예시
```
{
  "amotestnet2": {
    "address": "address",
    "pub_key": "pub_key",
    "priv_key": "priv_key",
    "encrypted": true
  },
  "testnet1214": {
    "address": "address",
    "pub_key": "pub_key",
    "priv_key": "priv_key",
    "encrypted": true
  }
}
```
TOBE 파일 예시
```
{"address":"address","encrypted":true,"priv_key":"priv_key","pub_key":"pub_key"}
```