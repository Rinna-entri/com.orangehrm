/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 91.30434782608695, "KoPercent": 8.695652173913043};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.8913043478260869, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "HTTP Request-3"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-16"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-2"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-15"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-5"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-14"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-4"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-13"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-19"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-1"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-18"], "isController": false}, {"data": [0.5, 500, 1500, "HTTP Request-0"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-17"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-12"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-11"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-10"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request-21"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-20"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-7"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-6"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-9"], "isController": false}, {"data": [1.0, 500, 1500, "HTTP Request-8"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 23, 2, 8.695652173913043, 533.9130434782609, 229, 6029, 231.0, 934.2000000000016, 5102.999999999987, 6029.0, 3.8148946757339526, 10.155990835959528, 2.529117079946923], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["HTTP Request-3", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 0.8793290043290043], "isController": false}, {"data": ["HTTP Request-16", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 1.9235321969696968], "isController": false}, {"data": ["HTTP Request-2", 1, 0, 0.0, 232.0, 232, 232, 232.0, 232.0, 232.0, 232.0, 4.310344827586206, 5.783607219827586, 0.7955616918103448], "isController": false}, {"data": ["HTTP Request-15", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 1.8432088744588744], "isController": false}, {"data": ["HTTP Request-5", 1, 0, 0.0, 232.0, 232, 232, 232.0, 232.0, 232.0, 232.0, 4.310344827586206, 5.783607219827586, 1.035492995689655], "isController": false}, {"data": ["HTTP Request-14", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 1.762885551948052], "isController": false}, {"data": ["HTTP Request-4", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 0.9596523268398268], "isController": false}, {"data": ["HTTP Request-13", 1, 0, 0.0, 237.0, 237, 237, 237.0, 237.0, 237.0, 237.0, 4.219409282700422, 5.661590189873418, 1.639965717299578], "isController": false}, {"data": ["HTTP Request-19", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 2.1645021645021645], "isController": false}, {"data": ["HTTP Request-1", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 0.7186823593073592], "isController": false}, {"data": ["HTTP Request-18", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 2.084178841991342], "isController": false}, {"data": ["HTTP Request-0", 1, 0, 0.0, 1399.0, 1399, 1399, 1399.0, 1399.0, 1399.0, 1399.0, 0.7147962830593281, 0.9591114188706219, 0.18567950321658327], "isController": false}, {"data": ["HTTP Request-17", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 2.003855519480519], "isController": false}, {"data": ["HTTP Request-12", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 1.602238906926407], "isController": false}, {"data": ["HTTP Request-11", 1, 0, 0.0, 230.0, 230, 230, 230.0, 230.0, 230.0, 230.0, 4.3478260869565215, 5.833899456521739, 1.528532608695652], "isController": false}, {"data": ["HTTP Request-10", 1, 0, 0.0, 229.0, 229, 229, 229.0, 229.0, 229.0, 229.0, 4.366812227074235, 5.859375, 1.4541825873362444], "isController": false}, {"data": ["HTTP Request-21", 1, 1, 100.0, 230.0, 230, 230, 230.0, 230.0, 230.0, 230.0, 4.3478260869565215, 10.597826086956522, 2.254585597826087], "isController": false}, {"data": ["HTTP Request-20", 1, 0, 0.0, 230.0, 230, 230, 230.0, 230.0, 230.0, 230.0, 4.3478260869565215, 5.833899456521739, 2.254585597826087], "isController": false}, {"data": ["HTTP Request-7", 1, 0, 0.0, 230.0, 230, 230, 230.0, 230.0, 230.0, 230.0, 4.3478260869565215, 5.833899456521739, 1.2058423913043477], "isController": false}, {"data": ["HTTP Request", 1, 1, 100.0, 6029.0, 6029, 6029, 6029.0, 6029.0, 6029.0, 6029.0, 0.1658649859014762, 5.077995417979764, 1.2645585399734616], "isController": false}, {"data": ["HTTP Request-6", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 1.1202989718614718], "isController": false}, {"data": ["HTTP Request-9", 1, 0, 0.0, 231.0, 231, 231, 231.0, 231.0, 231.0, 231.0, 4.329004329004329, 5.80864448051948, 1.3612689393939392], "isController": false}, {"data": ["HTTP Request-8", 1, 0, 0.0, 230.0, 230, 230, 230.0, 230.0, 230.0, 230.0, 4.3478260869565215, 5.833899456521739, 1.2865149456521738], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: java.io.IOException/Non HTTP response message: Exceeded maximum number of redirects: 20", 2, 100.0, 8.695652173913043], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 23, 2, "Non HTTP response code: java.io.IOException/Non HTTP response message: Exceeded maximum number of redirects: 20", 2, "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request-21", 1, 1, "Non HTTP response code: java.io.IOException/Non HTTP response message: Exceeded maximum number of redirects: 20", 1, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request", 1, 1, "Non HTTP response code: java.io.IOException/Non HTTP response message: Exceeded maximum number of redirects: 20", 1, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
