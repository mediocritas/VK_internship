# README.md

## Как запустить тесты параллельно через Gradle Wrapper

### Требования
- **Java 11**: Для запуска тестов требуется установленная версия Java 11. Убедитесь, что она установлена и доступна в PATH.
- **Gradle Wrapper**: Все команды запускаются с использованием Gradle Wrapper (`./gradlew` на Linux/Mac или `gradlew.bat` на Windows).

### Шаги для запуска тестов параллельно

1. **Убедитесь, что Java 11 установлена**

   Проверьте установленную версию Java командой:
   ```sh
   java -version
   ```

   2. **Запуск тестов параллельно**

      Для запуска тестов с использованием Gradle Wrapper выполните следующую команду:

      ```sh
      ./gradlew test --parallel -DBASE_URL=https://api.ok.ru/fb.do -DAPPLICATION_KEY=CBOCDILGDIHBABABA -DACCESS_TOKEN=-n-Vpte1ky5EgIEso1Qz8JWFafvx4AFttIoMpv46Y4ZWO3OevvPFuELxavDo3G6QhkBMfHYehvBGSNSVzLos0   
      ```

3. **Просмотр отчетов JUnit**
   После завершения тестирования, отчеты JUnit можно найти по следующему пути:
   ```
   build/reports/test/test/index.html
   ```