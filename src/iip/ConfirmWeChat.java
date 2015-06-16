package iip;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.WeixinMessageDigestUtil;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ConfirmWeChat extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public String execute() throws Exception {
        
        //获取请求和响应
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
       
        //获取请求参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostring = request.getParameter("echostr");
        
        String token = "mayday1991";    //你自己填写的token
        
        //对请求参数和自己的token进行排序，并连接排序后的结果为一个字符串
        String[] strSet = new String[]{token, timestamp, nonce};
        
        java.util.Arrays.sort(strSet);
        
        String total = "";
        for (String string : strSet) 
        {
            total = total + string;
        }
        
        //SHA-1加密实例  
        String codedString = WeixinMessageDigestUtil.getInstance().encipher(total);
        
        if (codedString.equals(signature)) 
        { 
        	//将加密的结果与请求参数中的signature比对，如果相同，原样返回echostr参数内容
        	PrintWriter out = response.getWriter();
            out.print(echostring);
            out.flush();
            out.close();
        }
        
        return null;
    }
	
}
