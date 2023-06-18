package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component //컴포넌트로 등록했기 때문에 CommonUtil 클래스는 스프링부트에 의해 관리되는 빈(자바 객체)
public class CommonUtil {
    public String markdown(String markdown) { //클라이언트가 입력한 markdown을 매개변수로 받음
        Parser parser = Parser.builder().build(); //마크다운 라이브러리를 통해서 parser를 얻음
        Node document = parser.parse(markdown); // markdown을 파서를 통해 파싱해서 '노드 트리'를 얻음

        HtmlRenderer renderer = HtmlRenderer.builder().build(); // '노드 트리'를 HTML로 랜더링하기 위한 객체를 GET
        return renderer.render(document); // HTML 랜더러로 노드트리를 HTML로 렌더링!
    }
}
