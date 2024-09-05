### This mod removes forbidden characters from signs, books, and when renaming items in anvil.

---
Default config values:
```yaml
# To create your regex, I recommend using https://regexr.com/ and https://www.compart.com/en/unicode/block 
# If whitelist is empty — all symbols will be deleted 
# If you want, you can allow all symbols by putting '.' into whitelist (whitelist: '.')
Regex:
  Sign:
    # Bypass permission: textfilter.signBypass
    whitelist: '[\u0021-\u007e а-я іїєґ]'
    blacklist: '[ыъэ]'
  Book:
    # Bypass permission: textfilter.bookBypass
    whitelist: '[\u0021-\u007e \n § а-я іїєґ]'
    blacklist: '[ыъэ]'
  Anvil:
    # Bypass permission: textfilter.anvilBypass
    whitelist: '[\u0021-\u007e а-я іїєґ]'
    blacklist: '[ыъэ]'
```

This mod uses [fabric-permission-api](https://github.com/lucko/fabric-permissions-api/). <br>
To manage these permission, you can use [LuckPerms](https://modrinth.com/mod/luckperms) or any other mod that can be used in this way. <br>
Also you can reload config in-game with `/textfilter reload` command (requires operator or `textfilter.reload` permission)

---
You can visit my little [contact card](https://somykos.github.io/web/), <br>
And you are welcome to support me via the following links:<br>
<a href="https://ko-fi.com/somyk">
<img src="https://raw.githubusercontent.com/somykOS/web/c03742bd86ca2ce0f6f39bcd3cfe683ad98926a2/public/external/kofi_s_logo_nolabel.svg" alt="ko-fi" width="100"/>
</a>
<a href="https://send.monobank.ua/jar/8RCzun35pC">
<img src="https://raw.githubusercontent.com/somykOS/web/5ac2e685429eb0cc369dc220ce3b93d2a22893c0/public/external/monobank_logo.svg" alt="monobank" width="80"/>
</a>