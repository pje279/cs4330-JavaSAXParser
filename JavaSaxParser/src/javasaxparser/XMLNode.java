/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Peter
 */
public class XMLNode
{
    public String name = "";
    public String content = "";
    public Map<String, List<XMLNode>> properties;
    public Map<String, String> attributes;
    
    public XMLNode()
    {
        this.name = "";
        this.content = "";
        this.properties = new LinkedHashMap<String, List<XMLNode>>();
        this.attributes = new LinkedHashMap<String, String>();
    } //End public XMLNode()
} //End public class XMLNode
