# ANNECT
## フラーの方へ
お忙しい中お時間いただき本当にありがとうございます。函館工業高等専門学校の川尻です。今回のプロジェクトのArduino通信以外の部分の開発を担当しました。読みにくいコードかと思いますが、よろしくお願い致します。

## アプリについて
### 開発環境
KotlinとJetpack ComposeとAndroidStdioを使って開発しています。

### 現状できている処理
初回起動時にキャラクタ(Anima)を作成し、そのデータをviewModelとprefarence DataStoreに保存し、ホーム画面に遷移します。二回目以降の起動時は、data storeからviewModelにデータを読みだします。
AnnectScreen.ktでviewModelを呼び出し、NavHostで画面遷移をしています。

データを消したいときは、ホーム画面のデータ消去ボタンを押してアプリを再起動してください。

## Arduinoとの通信について
ConnectScreen.ktでArduinoとの通信を行いたいです。この部分は今野が担当します。
実装したい事
Androidアプリケーションにおいて
USBホスト機能を用いて
Arduino とシリアル通信をして
Arduinoを制御すること

現在の実装方針
AndroidにはUSBホストの機能は
存在するがシリアル通信の機能はないらしいので、
USB-Serial-for-Android を用いて
シリアル通信の実装を考えている。

ーーー
困っていること
チュートリアルコードなどが
下のリポジトリにあるが
Kotlinしか齧ってきておらず
Javaのコードが読めずどう使っていいかわからない。

またライブラリもjavaで書かれているためKotlinでの使用方法がわからない。

ーーー
他に考えた事試したこと
スマホからロボットを操作するためBluetooth通信なども考えたが、Bluetoothの経験がなく躓いて
いる。

通信方法として
USB-HOSTモードではなく
accessoryモードもあるのでそちら側も考えてみたがそれはそれで通信の方法がわからない感じになっています。

テストとしてSerial USB Terminal というアプリケーションと、ライブラリのサンプルアプリケーションは動作確認は成功しているため
技術的には可能と見ています。


Developerガイド2つ
https://developer.android.com/guide/topics/connectivity/usb?hl=ja
https://developer.android.com/guide/topics/connectivity/usb/host?hl=ja

ライブラリURL
https://github.com/mik3y/usb-serial-for-android

色々参考にしていたURLなど

http://shokai.org/blog/archives/6962

https://qiita.com/gpsnmeajp/items/f360f200cb92f97d2895

https://ak1211.com/7707/

今回はArduino MEGAなので
idVendor:                        0x2341 = Arduino, LLC
idProduct:                       0x0042
10進数で9025
66
