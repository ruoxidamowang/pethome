// 获取文件类型
export function getFileType(file) {
	if (file) file = file.toLowerCase(file)
	let type = 'other'
	let pdfReg = /^.+(\.pdf)$/,
		txtReg = /^.+(\.txt)$/,
		wordReg = /^.+(\.doc|\.docx)$/,
		excelReg = /^.+(\.xls|\.xlsx)$/,
		jpgReg = /^.+(\.png|\.jpg|\.jpeg|\.bmp|\.gif|\.icon|\.jfif|\.JFIF)$/,
		bimReg = /^.+(\.rvt|\.rfa|\.dwg|\.dxf|\.spk|\.ifc|\.dgn|\.obj|\.stl|\.3ds|\.dae|\.ply|\.igms|\.zip|\.fbx|\.dwf|\.nwd|\.bmv)$/,
		video = /^.+(\.mov|\.mp4)$/
	if (pdfReg.test(file)) type = 'pdf'
	if (txtReg.test(file)) type = 'txt'
	if (wordReg.test(file)) type = 'word'
	if (excelReg.test(file)) type = 'excel'
	if (jpgReg.test(file)) type = 'picture'
	if (bimReg.test(file)) type = 'bim'
	if (video.test(file)) type = 'video'
	return type
}

// 阿拉伯数字转为汉字
export function formatNumber(number) {
	const chnNumChar = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九']
	const chnUnitSection = ['', '万', '亿', '万亿', '亿亿']
	const chnUnitChar = ['', '十', '百', '千']

	// 节内转换算法
	function SectionToChinese(section) {
		let strIns = ''
		let chnStr = ''
		let unitPos = 0
		let zero = true
		while (section > 0) {
			const v = section % 10
			if (v === 0) {
				if (!zero) {
					zero = true
					chnStr = chnNumChar[v] + chnStr
				}
			} else {
				zero = false
				strIns = chnNumChar[v]
				strIns += chnUnitChar[unitPos]
				chnStr = strIns + chnStr
			}
			unitPos++
			section = Math.floor(section / 10)
		}
		return chnStr
	}
	// 转换算法主函数
	function NumberToChinese(num) {
		let unitPos = 0
		let strIns = ''
		let chnStr = ''
		let needZero = false

		if (num === 0) {
			return chnNumChar[0]
		}
		while (num > 0) {
			const section = num % 10000
			if (needZero) {
				chnStr = chnNumChar[0] + chnStr
			}
			strIns = SectionToChinese(section)
			strIns += section !== 0 ? chnUnitSection[unitPos] : chnUnitSection[0]
			chnStr = strIns + chnStr
			needZero = section < 1000 && section > 0
			num = Math.floor(num / 10000)
			unitPos++
		}
		return chnStr
	}
	return NumberToChinese(number)
}

// 下载 Excel
export function downloadExcel(res, fileName = 'Excel - 信息表') {
	const link = document.createElement('a')
	const blob = new Blob([res], {
		type: 'application/vnd.ms-excel; charset=UTF-8;'
	})
	link.style.display = 'none'
	link.href = URL.createObjectURL(blob)
	link.download = fileName + '.xlsx'
	document.body.appendChild(link)
	link.click()
	document.body.removeChild(link)
}

/**
 * 下载excel
 * @param  data  接口返回数据 res.data
 * @param res 接口返回数据 res
 * @returns excel文件
 */
export function downloadFileNameExcel(data, res) {
	if (!data) {
		// 如果没有data数据就不进行操作
		return
	}

	// 获取headers中的filename文件名
	var tempName = res.data.data.headers['Content-Disposition'].split(';')[1].split('filename=')[1]

	// iconv-lite解决中文乱码
	const iconv = require('iconv-lite')
	iconv.skipDecodeWarning = true // 忽略警告
	const fileName = iconv.decode(tempName, 'gbk')
	const blob = new Blob([data], { type: 'application/vnd.ms-excel' }) // 转换成二进制对象
	if ('download' in document.createElement('a')) {
		// 不是IE浏览器
		const url = window.URL.createObjectURL(blob) // 二进制对象转换成url地址
		const link = document.createElement('a')
		link.style.display = 'none'
		link.href = url
		link.setAttribute('download', fileName)
		document.body.appendChild(link)
		link.click()
		document.body.removeChild(link) // 下载完成移除元素
		window.URL.revokeObjectURL(url) // 释放掉blob对象
	} else {
		window.navigator.msSaveBlob(blob, fileName)
	}
}
