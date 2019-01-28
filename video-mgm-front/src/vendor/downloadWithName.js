function addScript(url) {
    const callbackURL = `${url}?callback=foo`
    var script = document.createElement("script");
    script.setAttribute("src",callbackURL);
    document.getElementsByTagName("body")[0].appendChild(script);
}   

function getBlob(url) {
  return new Promise(resolve => {
      const xhr = new XMLHttpRequest();
      xhr.open('GET', url, true);
      xhr.responseType = 'blob';
      xhr.onload = () => {
          if (xhr.status === 200) {
              console.log(xhr.response)
              resolve(xhr.response);
          }
      };
      xhr.send();
  });
}

/**
* 保存
* @param {Blob} blob
* @param {String} filename 想要保存的文件名称
*/
function saveAs(blob, filename) {
  if (window.navigator.msSaveOrOpenBlob) {
      navigator.msSaveBlob(blob, filename);
  } else {
      const link = document.createElement('a');
      const body = document.querySelector('body');

      link.href = window.URL.createObjectURL(blob);
      link.download = filename;

      // fix Firefox
      link.style.display = 'none';
      body.appendChild(link);
      
      link.click();
      body.removeChild(link);

      window.URL.revokeObjectURL(link.href);
  }
}

/**
* 下载
* @param {String} url 目标文件地址
* @param {String} filename 想要保存的文件名称
*/
function downloadWithName(url, filename) {
  getBlob(url).then(blob => {
      saveAs(blob, filename);
  });
}


export {
    addScript,
    downloadWithName
}
