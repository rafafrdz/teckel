# ETL Grammar Ideas

version 1

```yaml
etl:
  name: table1
  join: null
  right:
    - table2:
        - 't2pk1:t1pk1'
        - 't2pk2:t1pk2'
    - table3:
        - 't3pk1:t1pk1'
  predicate:
    - expr: expr
```

version 2

```yaml
- name: tableResult
  join:
    joinType: left
    relation:
      - table1:
          - t1pk1: t2pk1
          - t1pk2: t2pk1
      - table2:
          - t3pk1: t1pk1
          - t3pk2: t1pk1

- name: table2
  source:
    path: 'prefix://bucket1/path1/path2'

```

version 3

```txt
Source         ::= Unknown | From
From           ::= `From` <String> <Operation> <Select> <Where>
Unknown        ::= `Unknown` <String>

Operation      ::= Input | Transformation | Output
Input          ::= `Input` <Format> [<Option>] <String>
Output         ::= `Output` <Format> [<Option>] <String>
Format         ::= `CSV` | `PARQUET` | `JSON` | `OCR` | ...
Option         ::= `Option` <String> <String>

Transformation ::= JoinOperation | GroupOperation | WindowOperation

// Join
JoinOperation  ::= `Join` <JoinType> <JoinRelation>
JoinType       ::= `Inner` | `Left` | `Right` | `Cross` | ...
JoinRelation   ::= `JoinRelation` <Source> <Source> [ <RelationField> ] 
RelationField  ::= `RelationField` <Column> <Column>

// Group
GroupOperation ::= `Group` <Source> <By> <Agg>
By             ::= `By` [Column]
Agg            ::= `Agg` [Column]

Select         ::= `Select` [Column]
Where          ::= `Where` [Column]
```

```yaml
## Inputs

input:
 - name: table1
   format: csv
   path: 'prefix://bucket1/path/path1'
   options:
    - header: true
    - sep: ','

 - name: table2
   format: parquet
   select:
    - t2pk1
    - t2pk2
    - field2
   path: 'prefix://bucket1/path/path2'
   
 - name: table5
   format: json
   path: 'prefix://bucket1/path/path5'
   
 - name: table6
   format: csv
   select:
    - t6pk1
    - t6pk2
    - field6
   path: 'prefix://bucket1/path/path6'
   options:
    - header: true
    - sep: '|'
    
 - name: table8
   format: parquet
   path: 'prefix://bucket1/path/path8'


## Transformations

transformation:
  - name: tableFinal
    join:
      joinType: left
      relation:
        left: table1
        right:
          - name: table2
            fields:
             - t1pk1:t2pk1 # left : right
             - t1pk2:t2pk2 # table1 : table2

          - name: table3
            fields:
             - t1pk1:t3pk1 # table1 : table3
             - t1pk2:t3pk2 # table1 : table3
  
          - name: table4
            fields:
             - t1pk1:t4pk1 # table1 : table4

  - name: table3
    group:
     name: table8
     by:
      - f1
      - f2
     agg:
      - expr1

  - name: table4
    join:
     joinType: inner
     relation:
      left: table6
      right:
       - name: table7
         fields:
         - t6pk1:t7pk1 # left : right
         - t6pk2:t7pk2 # table6 : table7

## Output

output:
 - name: tableFinal
   format: parquet
   path: 'prefix://bucket2/path/path1'
   
 - name: table4
   format: parquet
   path: 'prefix://bucket2/path/path2'
```
