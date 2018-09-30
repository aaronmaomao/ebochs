/**
 *
 */

/**
 * hostTable
 */
function hostTableInit() {
	$('#hostTable').treegrid({
		idField : 'NO',
		treeField : 'name',
		// title : '任务',
		width : '100%',
		height : 300,
		// border : false,
		striped : true,
		columns : [[{
			title : '序号',
			field : 'NO',
			width : 40
		}, {
			title : '名称',
			field : 'name',
			width : 100,
		}, {
			field : 'other',
			title : '其他',
			width : 80
		}]]
	});
	$($('#hostTable').datagrid('getPanel')).panel({
		headerCls : 'panelHeader'
	});
}

/**
 * regTable
 */
function regTableInit() {
	$('#regTable').datagrid({
		width : '100%',
		//	height : 300,
		// border : false,
		striped : true,
		title : '寄存器',
		showHeader : false,
		singleSelect : true,
		data : [{
			name1 : 'EAX',
			value1 : '0x00000000',
			name2 : 'EBP',
			value2 : '0x00000000'
		}, {
			name1 : 'ECX',
			value1 : '0x00000000',
			name2 : 'ESP',
			value2 : '0x00000000'
		}, {
			name1 : 'EDX',
			value1 : '0x00000000',
			name2 : 'ESI',
			value2 : '0x00000000'
		}, {
			name1 : 'EBX',
			value1 : '0x00000000',
			name2 : 'EDI',
			value2 : '0x00000000'
		}, {
			name1 : '',
			value1 : '',
			name2 : '',
			value2 : ''
		}, {
			name1 : 'CS',
			value1 : '0x0000',
			name2 : 'EIP',
			value2 : '0x00000000'
		}, {
			name1 : 'SS',
			value1 : '0x0000',
			name2 : 'ESP',
			value2 : '0x00000000'
		}, {
			name1 : 'DS',
			value1 : '0x0000',
			name2 : 'ES',
			value2 : '0x0000'
		}, {
			name1 : 'FS',
			value1 : '0x0000',
			name2 : 'GS',
			value2 : '0x0000'
		}, {
			name1 : 'FLG',
			value1 : '',
			name2 : '',
			value2 : ''
		}],
		columns : [[{
			title : '名称',
			field : 'name1',
			width : 40
		}, {
			title : '值',
			field : 'value1',
			width : 90,
		}, {
			title : '名称',
			field : 'name2',
			width : 40
		}, {
			title : '值',
			field : 'value2',
			width : 90,
		}]]
	});

	$($('#regTable').datagrid('getPanel')).panel({
		headerCls : 'panelHeader'
	});
}
	
/*
 * memory
 */