/* eslint-disable */
require('script-loader!file-saver');
require('script-loader!vendor/blob');
require('script-loader!xlsx-style/dist/xlsx.core.min');

function generateArray(table) {
    var out = [];
    var rows = table.querySelectorAll('tr');
    var ranges = [];
    for (var R = 0; R < rows.length; ++R) {
        var outRow = [];
        var row = rows[R];
        var columns = row.querySelectorAll('td');
        for (var C = 0; C < columns.length; ++C) {
            var cell = columns[C];
            var colspan = cell.getAttribute('colspan');
            var rowspan = cell.getAttribute('rowspan');
            var cellValue = cell.innerText;
            if (cellValue !== "" && cellValue == +cellValue) cellValue = +cellValue;

            //Skip ranges
            ranges.forEach(function (range) {
                if (R >= range.s.r && R <= range.e.r && outRow.length >= range.s.c && outRow.length <= range.e.c) {
                    for (var i = 0; i <= range.e.c - range.s.c; ++i) outRow.push(null);
                }
            });

            //Handle Row Span
            if (rowspan || colspan) {
                rowspan = rowspan || 1;
                colspan = colspan || 1;
                ranges.push({s: {r: R, c: outRow.length}, e: {r: R + rowspan - 1, c: outRow.length + colspan - 1}});
            }
            ;

            //Handle Value
            outRow.push(cellValue !== "" ? cellValue : null);

            //Handle Colspan
            if (colspan) for (var k = 0; k < colspan - 1; ++k) outRow.push(null);
        }
        out.push(outRow);
    }
    return [out, ranges];
};

function datenum(v, date1904) {
    if (date1904) v += 1462;
    var epoch = Date.parse(v);
    return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
}

function sheet_from_cellArray_of_cellArrays(data) {
    var ws = {};
    var range = {s: {c: 10000000, r: 10000000}, e: {c: 0, r: 0}};
    for (var R = 0; R != data.length; ++R) {
        for (var C = 0; C != data[R].length; ++C) {
            if (range.s.r > R) range.s.r = R;
            if (range.s.c > C) range.s.c = C;
            if (range.e.r < R) range.e.r = R;
            if (range.e.c < C) range.e.c = C;

            var cell = data[R][C];
            if (cell.v == null) continue;
            var cell_ref = XLSX.utils.encode_cell({c: C, r: R});

            if (typeof cell.v === 'number') cell.t = 'n';
            else if (typeof cell.v === 'boolean') cell.t = 'b';
            else if (cell.v instanceof Date) {
                cell.t = 'n';
                cell.z = XLSX.SSF._table[14];
                cell.v = datenum(cell.v);
            }
            else cell.t = 's';

            ws[cell_ref] = cell;
        }
    }
    if (range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
    return ws;
}

function sheet_from_array_of_arrays(data, opts) {
    var ws = {};
    var range = {s: {c: 10000000, r: 10000000}, e: {c: 0, r: 0}};
    for (var R = 0; R != data.length; ++R) {
        for (var C = 0; C != data[R].length; ++C) {
            if (range.s.r > R) range.s.r = R;
            if (range.s.c > C) range.s.c = C;
            if (range.e.r < R) range.e.r = R;
            if (range.e.c < C) range.e.c = C;
            var cell = {
                v: data[R][C],
                s: {
                    font: {
                        name: 'Arial',
                        sz: '10'
                    },
                }
            };
            if (cell.v == null) continue;
            var cell_ref = XLSX.utils.encode_cell({c: C, r: R});

            if (typeof cell.v === 'number') cell.t = 'n';
            else if (typeof cell.v === 'boolean') cell.t = 'b';
            else if (cell.v instanceof Date) {
                cell.t = 'n';
                cell.z = XLSX.SSF._table[14];
                cell.v = datenum(cell.v);
            }
            else cell.t = 's';

            ws[cell_ref] = cell;
        }
    }
    if (range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
    return ws;
}

function Workbook() {
    if (!(this instanceof Workbook)) return new Workbook();
    this.SheetNames = [];
    this.Sheets = {};
}

function s2ab(s) {
    var buf = new ArrayBuffer(s.length);
    var view = new Uint8Array(buf);
    for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
    return buf;
}

export function export_table_to_excel(id) {
    var theTable = document.getElementById(id);
    console.log('a')
    var oo = generateArray(theTable);
    var ranges = oo[1];

    /* original data */
    var data = oo[0];
    var ws_name = "SheetJS";
    console.log(data);

    var wb = new Workbook(), ws = sheet_from_array_of_arrays(data);

    /* add ranges to worksheet */
    // ws['!cols'] = ['apple', 'banan'];
    ws['!merges'] = ranges;

    /* add worksheet to workbook */
    wb.SheetNames.push(ws_name);
    wb.Sheets[ws_name] = ws;

    var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: false, type: 'binary'});

    saveAs(new Blob([s2ab(wbout)], {type: "application/octet-stream"}), "test.xlsx")
}

export function export_json_to_excel(th, jsonData, defaultTitle,defaultSheet,autoWidth) {

    /* original data */

    var data = jsonData;
    data.unshift(th);
    var ws_name = defaultSheet || 'Sheet';

    var wb = new Workbook(), ws = sheet_from_array_of_arrays(data);

    if(autoWidth){
      const maxWidth = 50;
      const minWidth = 10;
        /*设置worksheet每列的最大宽度*/
        const colWidth = data.map(row => row.map(val => {
            /*先判断是否为null/undefined*/
            if (val == null) {
                return {'wch': 10};
            }
            /*再判断是否为中文*/
            else if (val.toString().charCodeAt(0) > 255) {
              if(val.toString().length * 1.62> maxWidth){
                return {'wch': maxWidth};
              } else if (val.toString().length * 1.62< minWidth) {
                return {'wch': minWidth };
              }else {
                return {'wch':  val.toString().length * 1.62};
              }
            } else {
                return {'wch': val.toString().length  > maxWidth ? maxWidth:val.toString().length };
            }
        }))
        /*以第一行为初始值*/
        let result = colWidth[0];
        for (let i = 1; i < colWidth.length; i++) {
            for (let j = 0; j < colWidth[i].length; j++) {
                if (result[j]['wch'] < colWidth[i][j]['wch']) {
                    result[j]['wch'] = colWidth[i][j]['wch'];
                }
            }
        }
        ws['!cols'] = result;
        // 设置列宽结束
    }

    /* add worksheet to workbook */
    wb.SheetNames.push(ws_name);
    wb.Sheets[ws_name] = ws;

    var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: false, type: 'binary'});
    var title = defaultTitle || '列表' ;
    saveAs(new Blob([s2ab(wbout)], {type: "application/octet-stream"}), title + ".xlsx")
}


/**
 * 将多个json数据以不同的worksheet转换成Excel
 * @param datas  [{{'sheetName': 'xxx','head':[{},{}, ...],'data':[[{},{},..],[{},{},...]]}, {...}]
 * @param workbookName
 * 数组里面每个元素都是cell对象{}，键v - 值， 键s - 样式
 */
export function exportJsonToExcel(datas, workbookName) {
    /* create workbook */
    var wb = new Workbook();
    var wb_name = workbookName || "数据列表";

    for(let {sheetName, head, data} of datas) {

        //插入头部
        if(head) {
            data.unshift(head);
        }

        /* worksheet name */
        var ws_name = sheetName || "Sheet1";

        /* create worksheet from data */
        var ws = sheet_from_cellArray_of_cellArrays(data);

        /*设置worksheet每列的最大宽度*/
        const temp = data.map(row => row.map(val => {return {'wch': val.toString().length}}));
        /*以第一行为初始值*/
        let result = temp[0];
        for(let i = 1; i < temp.length; i++){
            for(let m = 0; m < temp[i].length; m++){
                if(result[m]['wch'] < temp[i][m]['wch']){
                    result[m]['wch'] = temp[i][m]['wch'];
                }
            }
        }
        ws['!cols'] = result.map(obj => {obj['wch'] = obj['wch']+5; return obj;});


        /* add worksheet to workbook */
        wb.SheetNames.push(ws_name);
        wb.Sheets[ws_name] = ws;
    }

    var wbout = XLSX.write(wb, {bookType:'xlsx', bookSST:true, type: 'binary'});

    /* the saveAs call downloads a file on the local machine */
    saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), wb_name.concat('.xlsx'));

}
