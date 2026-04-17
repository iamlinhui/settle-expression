# Settle Expression

轻量级 Java 表达式引擎，基于调度场算法（Shunting Yard）和逆波兰表示法（RPN）实现。支持数学运算、逻辑判断、日期计算、方法调用、Spring Bean 访问等功能，适用于规则引擎、金融结算等场景。

## 特性

- 基于 `BigDecimal` 的高精度数值运算，避免浮点误差
- 支持中文变量名
- 支持对象属性访问、Map/List 操作
- 内置日期计算（间隔天数、日期偏移）
- 内置精度控制函数（四舍五入、向上/向下取整）
- 支持三元表达式
- 支持 Spring Bean 注入访问
- 支持表达式预编译与模板渲染
- 可通过 SPI 扩展自定义函数

## 快速开始

### Maven 依赖

```xml
<dependency>
    <groupId>cn.holmes</groupId>
    <artifactId>settle-expression</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 基本用法

```java
// 算术运算
El.eval("1 + 2 * 3");        // 7
El.eval("(1 + 2) * 3");      // 9

// 比较运算
El.eval("-3 > -6");           // true
El.eval("3 >= 3");            // true

// 逻辑运算
El.eval("true && false");     // false
El.eval("!false");            // true

// 三元表达式
El.eval("1 > 2 ? 'aaa' : 'bbb'");  // "bbb"

// 类型转换
El.eval("1.223", Integer.class);     // 1
El.eval("1 + 2", BigDecimal.class);  // 3
```

## 运算符

| 符号 | 操作数 | 优先级 | 说明 |
|------|--------|--------|------|
| `()` | * | 100 | 括号，优先计算 |
| `.` | 2 | 1 | 属性访问、方法调用、链式计算 |
| `['key']` | 2 | 1 | Map 按字符串键取值 |
| `[index]` | 2 | 1 | List/数组 下标访问 |
| `,` | * | 0 | 方法参数分隔符 |
| `-` (负号) | 1 | 2 | 取负 |
| `~` | 1 | 2 | 按位取反 |
| `*` | 2 | 3 | 乘法 |
| `/` | 2 | 3 | 除法 |
| `%` | 2 | 3 | 取模 |
| `+` | 2 | 4 | 加法 / 字符串拼接 |
| `-` | 2 | 4 | 减法 / 日期偏移 |
| `<<` | 2 | 5 | 左移 |
| `>>` | 2 | 5 | 右移 |
| `>>>` | 2 | 5 | 无符号右移 |
| `<=` | 2 | 5 | 小于等于 |
| `>` | 2 | 6 | 大于 |
| `<` | 2 | 6 | 小于 |
| `>=` | 2 | 6 | 大于等于 |
| `!=` | 2 | 6 | 不等于 |
| `!` | 1 | 7 | 逻辑非 |
| `==` | 2 | 7 | 等于 |
| `&` | 2 | 8 | 按位与 |
| `^` | 2 | 9 | 按位异或 |
| `\|` | 2 | 10 | 按位或 |
| `&&` | 2 | 11 | 逻辑与 |
| `\|\|` | 2 | 12 | 逻辑或 |
| `?:` | 3 | 13 | 三元运算 |

## 内置函数

| 函数 | 参数 | 说明 | 示例 |
|------|------|------|------|
| `half_up` | 1 | 四舍五入精度 | `1.div(3, half_up(2))` → `0.33` |
| `half_down` | 1 | 五舍六入精度 | `1.div(3, half_down(2))` |
| `up` | 1 | 向上取整精度 | `1.div(3, up(2))` |
| `down` | 1 | 向下取整精度 | `1.div(3, down(2))` |
| `max` | * | 取最大值 | `max(1, 2, 3, 99, 54)` → `99` |
| `min` | * | 取最小值 | `min(-100, 2, 3)` → `-100` |
| `days` | 2 | 日期间隔天数（≥0） | `days('2022-07-01', 20220728)` → `27` |
| `now` | 0 或 1 | 当前时间 | `now()` 返回时间戳；`now('yyyy-MM-dd')` 返回格式化字符串 |
| `trim` | 1 | 去除两端空格 | `trim(' hello ')` → `hello` |

## 数值方法（链式调用）

数值支持通过 `.` 进行链式方法调用，底层基于 `BigDecimal` 实现高精度计算：

| 方法 | 说明 | 示例 |
|------|------|------|
| `.div(divisor)` | 除法（默认64位精度） | `1.div(3)` |
| `.div(divisor, precision)` | 除法（指定精度） | `1.div(3, half_up(9))` → `0.333333333` |
| `.mul(multiplicand)` | 乘法 | `0.333.mul(3)` |
| `.mul(multiplicand, precision)` | 乘法（指定精度） | `1.div(3, half_up(9)).mul(3, half_up(5))` |
| `.add(augend)` | 加法 | `1.add(2)` |
| `.sub(subtrahend)` | 减法 | `3.sub(1)` |
| `.pow(index)` | 幂运算 | `(0.006667+1).pow(3)` |
| `.off(precision)` | 精度取舍 | `(1/3*3).off(half_up(2))` → `1.00` |
| `.max(other)` | 取较大值 | `3.max(5)` → `5` |
| `.min(other)` | 取较小值 | `3.min(5)` → `3` |

## 使用示例

### 上下文变量

```java
Context context = Lang.context();
context.set("price", 100);
context.set("quantity", 5);
El.eval(context, "price * quantity");  // 500
```

支持将对象属性批量导入上下文：

```java
Context context = Lang.context();
Demo demo = new Demo("吴彦祖");
context.putAll(demo);
El.eval(context, "name");  // "吴彦祖"
```

### 对象属性访问

```java
Context context = Lang.context();
context.set("demo", new Demo("吴彦祖"));
El.eval(context, "demo.name");  // "吴彦祖"
```

### List / 数组操作

```java
Context context = Lang.context();
context.set("array", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

El.eval(context, "array[5]");         // 6
El.eval(context, "array.size");       // 8
El.eval(context, "array.isEmpty()");  // false
El.eval(context, "!array.isEmpty()"); // true
```

### Map 操作

```java
Map<String, Object> map = new HashMap<>();
map.put("a", "aaa");
map.put("d", "ddd");
Context context = Lang.context();
context.set("map", map);

El.eval(context, "map[a]");  // "aaa"
El.eval(context, "map.d");   // "ddd"
```

### 日期计算

```java
Context context = Lang.context();
context.set("剩余本金", 1000);
context.set("年利率", 0.08);
context.set("起息日", new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-02"));
context.set("结算日", new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-21"));

// 利息计算：本金 × 年利率 ÷ 360 × 天数
BigDecimal interest = El.eval(context, "剩余本金*年利率/360*((结算日-1-起息日))", BigDecimal.class);
interest.setScale(2, RoundingMode.HALF_UP);  // 4.00

// 间隔天数
El.eval(context, "days(结算日,起息日)", Integer.class);

// 日期字符串也支持
El.eval("days('2022-07-01', 20220728)", Integer.class);  // 27
```

### 高精度金融计算（PMT 公式）

```java
// 等额本息月供公式：PMT = P*r*(1+r)^n / ((1+r)^n - 1)
String el = "(10000*0.006666666667*(0.006666666667+1).pow(3)).div((0.006666666667+1).pow(3)-1,half_up(2))";
El.eval(el);  // 3377.88
```

### 链式精度控制

```java
El.eval("1.div(3,half_up(9)).mul(3).off(half_up(0))");       // 1
El.eval("1.div(3,half_up(9)).mul(3,half_up(5))");            // 0.99999
El.eval("1.div(3,half_up(9))");                               // 0.333333333
El.eval("(1 / 3 * 3).off(half_up(2))");                      // 1.00
```

### Spring Bean 访问

```java
// 使用 @ 前缀获取 Spring IOC 中的 Bean
El.eval("@springFramework");
```

### 表达式预编译

```java
// 预编译提升重复执行性能
El el = new El("price * quantity");
Context context = Lang.context();
context.set("price", 100);
context.set("quantity", 5);
el.eval(context);  // 500
```

### 模板渲染

```java
Segment segment = Segments.create("${aaa}");
segment.set("aaa", "bbb");
segment.render();  // "bbb"
```

## 扩展自定义函数

通过 SPI 机制扩展自定义函数，实现 `RunMethod` 接口并注册到 `META-INF/services/cn.holmes.settle.expression.lang.opt.RunMethod`：

```java
// 方式一：SPI 注册
public class MyFunction implements RunMethod {
    public String fetchSelf() { return "myFunc"; }
    public int fetchArgSize() { return 1; }
    public Object run(Queue<Object> args) { /* ... */ }
}

// 方式二：代码注册
El.register("myFunc", (args) -> { /* ... */ });
```

## 技术实现

- **调度场算法（Shunting Yard）**：将中缀表达式转换为后缀表达式（逆波兰表示法）
- **逆波兰求值（RPN）**：基于栈的后缀表达式求值
- **高精度数值**：所有数值运算基于 `BigDecimal`，封装为 `SettleDecimal`
- **Spring 集成**：通过 `spring.factories` 自动配置，支持 `@` 前缀访问 Spring Bean

## 环境要求

- Java 8+
- Spring Framework 5.x（可选，仅 Bean 访问功能需要）
