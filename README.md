### 运算符

| 符号    | 操作数 | 权重 | 解释                                                         |
| ------- | ------ | ---- | ------------------------------------------------------------ |
| ()      | *      | 100  | 括号，优先计算                                               |
| ,       | *      | 0    | 逗号，主要是方法参数                                         |
| .       | 2      | 1    | 访问对象的属性，或者Map的值，或者方法调用，或者自定义函数调用（需要结合后面是否有括号） |
| ['abc'] | 2      | 1    | Map对象 按字符串键值获得值                                   |
| [3]     | 2      | 1    | 数字，列表，或者集合的下标访问符号                           |
| *       | 2      | 3    | 乘                                                           |
| /       | 2      | 3    | 除                                                           |
| %       | 2      | 3    | 取模                                                         |
| +       | 2      | 4    | 加                                                           |
| -       | 2      | 4    | 减                                                           |
| -       | 2      | 2    | 负                                                           |
| >=      | 2      | 6    | 大于等于                                                     |
| <=      | 2      | 5    | 小于等于                                                     |
| ==      | 2      | 7    | 等于                                                         |
| !=      | 2      | 6    | 不等于                                                       |
| !       | 2      | 7    | 非                                                           |
| >       | 2      | 6    | 大于                                                         |
| <       | 2      | 6    | 小于                                                         |
| &&      | 2      | 11   | 逻辑与                                                       |
| \|\|    | 2      | 12   | 逻辑或                                                       |
| ?:      | 2      | 13   | 三元运算                                                     |
| &       | 2      | 8    | 位运算，与                                                   |
| ~       | 2      | 2    | 位运算，非                                                   |
| \|      | 2      | 10   | 位运算，或                                                   |
| ^       | 2      | 9    | 位运算，异或                                                 |
| <<      | 2      | 5    | 位运算，左移                                                 |
| >>      | 2      | 5    | 位运算，右移                                                 |
| >>>     | 2      | 5    | 位运算，无符号右移                                           |

### 函数集

| 函数名    | 操作数 | 解释                                                     |
| --------- | ------ | -------------------------------------------------------- |
| days      | 2      | 计算两个Date之间的间隔天数                               |
| half_up   | 1      | 四舍五入                                                 |
| half_down | 1      | 五舍六入                                                 |
| up        | 1      | 向上取整                                                 |
| down      | 1      | 向下取整                                                 |
| max       | *      | 比较出最大值                                             |
| min       | *      | 比较出最小值                                             |
| now       | 0或者1 | 当前时间，为0时返回时间戳，为1时可输入时间格式yyyy-MM-dd |
| trim      | 1      | 去掉字符串两边的空格                                     |