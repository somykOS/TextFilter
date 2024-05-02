### This mod removes forbidden characters from signs, books, and when renaming items in anvil.

---
Default config values:
```yaml
# To create your regex, I recommend using https://regexr.com/ and https://www.compart.com/en/unicode/block
Regex:
  Sign:
    # Bypass permission: TextFilter.signBypass
    whitelist: '[]'
    blacklist: '[^\s\u0021-\u007e\u0410-\u044fії]|[ыёъэ]'
  Book:
    # Bypass permission: TextFilter.bookBypass
    whitelist: '[]'
    blacklist: '[^\s\u0021-\u007e\u0410-\u044fії]|[ыёъэ]'
  Anvil:
    # Bypass permission: TextFilter.anvilBypass
    whitelist: '[]'
    blacklist: '[^\s\u0021-\u007e\u0410-\u044fії]|[ыёъэ]'
    Empty_symbol_replacement: Forbidden symbol
```

---

This mod uses [fabric-permission-api](https://github.com/lucko/fabric-permissions-api/). <br>
To manage these permission, you can use [LuckPerms](https://modrinth.com/mod/luckperms) or any other mod that can be used in this way. <br>

`Empty_symbol_replacement` is used when player renames item with blacklist symbols only.