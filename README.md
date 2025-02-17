# xml2axml

English | [中文](README_zh-CN.md)

Tool for encoding xml to axml OR decoding axml to xml

### Usage

#### xml to axml
``` shell
java -jar xml2axml-2.2.0.jar encode [AndroidManifest-readable-in.xml] [AndroidManifest-bin-out.xml]
```

#### axml to xml
``` shell
java -jar xml2axml-2.2.0.jar decode [AndroidManifest-bin-in.xml] [AndroidManifest-readable-out.xml]
```

### Building

```bash
./gradlew executable
```

Will produce two outputs in `/build/libs`:
- `xml2axml-2.2.0.jar`
- `xml2axml` - a binary executable that can be run standalone

### Acknowledgements
- [xml2axml](https://github.com/codyi96/xml2axml) by [codyi96](https://github.com/codyi96)
- [xml2axml](https://github.com/l741589/xml2axml) by [l741589](https://github.com/l741589)
- [xml2axml](https://github.com/hzw1199/xml2axml) by [hzw1199](https://github.com/hzw1199)
