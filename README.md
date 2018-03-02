A5Sqlコンバータ
=============

A5SQLで生成した `XXX.a5er` をDBオブジェクトに変換するためのライブラリ。

現在はEloquentのみをサポート。

カラム定数、getter/setterは作成するようにしているものの、

Repositoryは生成できないため、対応予定。


# 不具合情報
返ってくるzipのbyte配列がチェックサムができておらず

7zipでしか解凍できない。

# 使い方

### Eloquent例 (javaで記述)
```
A5SqlParser parser = A5SqlParser.Companion.newInstance("packageName");
DbData data =  parser.parse("path to XXX.a5er");

byte[] bytes = DbObjectBuilder.Companion.getEloquentBuilder(data).build();

File file = new File("/Users/hilo/Desktop/aaa.zip");
try (OutputStream out = new FileOutputStream(file)) {
    out.write(bytes);
}
```


