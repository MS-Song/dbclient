
// http request 에서 제외시킬 파라메터 목록
let excludeParams = ["useCache","apiAuthkey","page","size","sort"];

// swagger 정의를 가져온다.
let swaggerApiDocs = null;
let getSwaggerApiDocs = function(){
    if(null==swaggerApiDocs){
        webix.ajax().get("/v2/api-docs",function(text,data){
            swaggerApiDocs=data.json();
        });
    }
};

let getSwaggerApiDocsLazyLoading = function(){
    if(null==swaggerApiDocs){
        setTimeout(() => {getSwaggerApiDocsLazyLoading()}, 100);
    } else {
        getSwaggerApiDocs();
    }
}