package com.alphawallet.app.tokenscript;

import com.alphawallet.token.entity.As;
import com.alphawallet.token.entity.ParseResult;
import com.alphawallet.token.tools.TokenDefinition;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static com.alphawallet.token.tools.TokenDefinition.TOKENSCRIPT_CURRENT_SCHEMA;
import static com.alphawallet.token.tools.TokenDefinition.TOKENSCRIPT_MINIMUM_SCHEMA;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import timber.log.Timber;

public class TokenscriptFunctionTest implements ParseResult {
    //have to put file as a string because app cannot read files
    private final String entryTokenTestFile = "<ts:token xmlns:ethereum=\"urn:ethereum:constantinople\" xmlns:ts=\"http://tokenscript.org/2020/06/tokenscript\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" custodian=\"false\" xsi:schemaLocation=\"http://tokenscript.org/2020/06/tokenscript http://tokenscript.org/2020/06/tokenscript.xsd\">\n" +
            "  <ts:label>\n" +
            "    <ts:plurals xml:lang=\"en\">\n" +
            "      <ts:string quantity=\"one\">Ticket</ts:string>\n" +
            "      <ts:string quantity=\"other\">Tickets</ts:string>\n" +
            "    </ts:plurals>\n" +
            "    <ts:plurals xml:lang=\"es\">\n" +
            "      <ts:string quantity=\"one\">Boleto de admisión</ts:string>\n" +
            "      <ts:string quantity=\"other\">Boleto de admisiónes</ts:string>\n" +
            "    </ts:plurals>\n" +
            "    <ts:plurals xml:lang=\"zh\">\n" +
            "      <ts:string quantity=\"one\">入場券</ts:string>\n" +
            "      <ts:string quantity=\"other\">入場券</ts:string>\n" +
            "    </ts:plurals>\n" +
            "  </ts:label>\n" +
            "  <ts:contract interface=\"erc875\" name=\"EntryToken\">\n" +
            "    <ts:address network=\"1\">0x63cCEF733a093E5Bd773b41C96D3eCE361464942</ts:address>\n" +
            "    <ts:address network=\"3\">0xFB82A5a2922A249f32222316b9D1F5cbD3838678</ts:address>\n" +
            "    <ts:address network=\"42\">0x2B58A9403396463404c2e397DBF37c5EcCAb43e5</ts:address>\n" +
            "  </ts:contract>\n" +
            "  <ts:origins>\n" +
            "    <!-- Define the contract which holds the token that the user will use -->\n" +
            "    <ts:ethereum contract=\"EntryToken\"></ts:ethereum>\n" +
            "  </ts:origins>\n" +
            "    <ts:selection filter=\"expired=TRUE\" id=\"expired\">\n" +
            "      <ts:label>\n" +
            "        <ts:plurals xml:lang=\"en\">\n" +
            "          <ts:string quantity=\"one\">Expired Ticket</ts:string>\n" +
            "          <ts:string quantity=\"other\">Expired Tickets</ts:string>\n" +
            "        </ts:plurals>\n" +
            "        <ts:string xml:lang=\"zh\">已经过期的票</ts:string>\n" +
            "      </ts:label>\n" +
            "    </ts:selection>\n" +
            "  <ts:cards>\n" +
            "    <ts:card type=\"token\">\n" +
            "      <ts:item-view xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">\n" +
            "        <style type=\"text/css\">.ts-count {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: bolder;\n" +
            "  font-size: 21px;\n" +
            "  color: rgb(117, 185, 67);\n" +
            "}\n" +
            ".ts-category {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 21px;\n" +
            "  color: rgb(67, 67, 67);\n" +
            "}\n" +
            ".ts-venue {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 16px;\n" +
            "  color: rgb(67, 67, 67);\n" +
            "}\n" +
            ".ts-date {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: bold;\n" +
            "  font-size: 14px;\n" +
            "  color: rgb(112, 112, 112);\n" +
            "  margin-left: 7px;\n" +
            "  margin-right: 7px;\n" +
            "}\n" +
            ".ts-time {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 16px;\n" +
            "  color: rgb(112, 112, 112);\n" +
            "}\n" +
            "html {\n" +
            "}\n" +
            "\n" +
            "body {\n" +
            "padding: 0px;\n" +
            "margin: 0px;\n" +
            "}\n" +
            "\n" +
            "div {\n" +
            "margin: 0px;\n" +
            "padding: 0px;\n" +
            "}\n" +
            "\n" +
            ".data-icon {\n" +
            "height:16px;\n" +
            "vertical-align: middle\n" +
            "}\n" +
            "\n" +
            ".tbml-count {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: bolder;\u2028  font-size: 21px;\u2028  color: rgb(117, 185, 67);\u2028}\u2028.tbml-category {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 21px;\u2028  color: rgb(67, 67, 67);\u2028}\u2028.tbml-venue {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 16px;\u2028  color: rgb(67, 67, 67);\u2028}\u2028.tbml-date {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: bold;\u2028  font-size: 14px;\u2028  color: rgb(112, 112, 112);\u2028  margin-left: 7px;\u2028  margin-right: 7px;\u2028}\u2028.tbml-time {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 16px;\u2028  color: rgb(112, 112, 112);\u2028}\u2028   html {\u2028   }\u2028   \u2028   body {\u2028   padding: 0px;\u2028   margin: 0px;\u2028   }\u2028   \u2028   div {\u2028   margin: 0px;\u2028   padding: 0px;\u2028   }\u2028\u2028   .data-icon {\u2028   height:16px;\u2028   vertical-align: middle\u2028   }\u2028\n" +
            "\n" +
            "\n" +
            "</style>\n" +
            "        <body><div>\n" +
            "    <!-- Iconified view displayed on the first page when clicking on token card in DailyWallet -->\n" +
            "    <p>Enter Satoshi's villa with this special token!\n" +
            "        <img alt=\"\" src=\"data:image/jpeg;base64,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\"></img>\n" +
            "    </p>\n" +
            "</div>\n" +
            "</body>\n" +
            "      </ts:item-view>\n" +
            "      <ts:view xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">\n" +
            "        <style type=\"text/css\">.ts-count {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: bolder;\n" +
            "  font-size: 21px;\n" +
            "  color: rgb(117, 185, 67);\n" +
            "}\n" +
            ".ts-category {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 21px;\n" +
            "  color: rgb(67, 67, 67);\n" +
            "}\n" +
            ".ts-venue {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 16px;\n" +
            "  color: rgb(67, 67, 67);\n" +
            "}\n" +
            ".ts-date {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: bold;\n" +
            "  font-size: 14px;\n" +
            "  color: rgb(112, 112, 112);\n" +
            "  margin-left: 7px;\n" +
            "  margin-right: 7px;\n" +
            "}\n" +
            ".ts-time {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 16px;\n" +
            "  color: rgb(112, 112, 112);\n" +
            "}\n" +
            "html {\n" +
            "}\n" +
            "\n" +
            "body {\n" +
            "padding: 0px;\n" +
            "margin: 0px;\n" +
            "}\n" +
            "\n" +
            "div {\n" +
            "margin: 0px;\n" +
            "padding: 0px;\n" +
            "}\n" +
            "\n" +
            ".data-icon {\n" +
            "height:16px;\n" +
            "vertical-align: middle\n" +
            "}\n" +
            "\n" +
            ".tbml-count {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: bolder;\u2028  font-size: 21px;\u2028  color: rgb(117, 185, 67);\u2028}\u2028.tbml-category {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 21px;\u2028  color: rgb(67, 67, 67);\u2028}\u2028.tbml-venue {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 16px;\u2028  color: rgb(67, 67, 67);\u2028}\u2028.tbml-date {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: bold;\u2028  font-size: 14px;\u2028  color: rgb(112, 112, 112);\u2028  margin-left: 7px;\u2028  margin-right: 7px;\u2028}\u2028.tbml-time {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 16px;\u2028  color: rgb(112, 112, 112);\u2028}\u2028   html {\u2028   }\u2028   \u2028   body {\u2028   padding: 0px;\u2028   margin: 0px;\u2028   }\u2028   \u2028   div {\u2028   margin: 0px;\u2028   padding: 0px;\u2028   }\u2028\u2028   .data-icon {\u2028   height:16px;\u2028   vertical-align: middle\u2028   }\u2028\n" +
            "\n" +
            "\n" +
            "</style>\n" +
            "        <script type=\"text/javascript\">//\n" +
            "    (function() {\n" +
            "        'use strict'\n" +
            "        function GeneralizedTime(generalizedTime) {\n" +
            "            this.rawData = generalizedTime;\n" +
            "        }\n" +
            "\n" +
            "        GeneralizedTime.prototype.getYear = function () {\n" +
            "            return parseInt(this.rawData.substring(0, 4), 10);\n" +
            "        };\n" +
            "\n" +
            "        GeneralizedTime.prototype.getMonth = function () {\n" +
            "            return parseInt(this.rawData.substring(4, 6), 10) - 1;\n" +
            "        };\n" +
            "\n" +
            "        GeneralizedTime.prototype.getDay = function () {\n" +
            "            return parseInt(this.rawData.substring(6, 8), 10)\n" +
            "        };\n" +
            "\n" +
            "        GeneralizedTime.prototype.getHours = function () {\n" +
            "            return parseInt(this.rawData.substring(8, 10), 10)\n" +
            "        };\n" +
            "\n" +
            "        GeneralizedTime.prototype.getMinutes = function () {\n" +
            "            var minutes = parseInt(this.rawData.substring(10, 12), 10)\n" +
            "            if (minutes) return minutes\n" +
            "            return 0\n" +
            "        };\n" +
            "\n" +
            "        GeneralizedTime.prototype.getSeconds = function () {\n" +
            "            var seconds = parseInt(this.rawData.substring(12, 14), 10)\n" +
            "            if (seconds) return seconds\n" +
            "            return 0\n" +
            "        };\n" +
            "\n" +
            "        GeneralizedTime.prototype.getMilliseconds = function () {\n" +
            "            var startIdx\n" +
            "            if (time.indexOf('.') !== -1) {\n" +
            "                startIdx = this.rawData.indexOf('.') + 1\n" +
            "            } else if (time.indexOf(',') !== -1) {\n" +
            "                startIdx = this.rawData.indexOf(',') + 1\n" +
            "            } else {\n" +
            "                return 0\n" +
            "            }\n" +
            "\n" +
            "            var stopIdx = time.length - 1\n" +
            "            var fraction = '0' + '.' + time.substring(startIdx, stopIdx)\n" +
            "            var ms = parseFloat(fraction) * 1000\n" +
            "            return ms\n" +
            "        };\n" +
            "\n" +
            "        GeneralizedTime.prototype.getTimeZone = function () {\n" +
            "            let time = this.rawData;\n" +
            "            var length = time.length\n" +
            "            var symbolIdx\n" +
            "            if (time.charAt(length - 1 ) === 'Z'){\n" +
            "                return 0\n" +
            "            }\n" +
            "            if (time.indexOf('+') !== -1) {\n" +
            "                symbolIdx = time.indexOf('+')\n" +
            "            } else if (time.indexOf('-') !== -1) {\n" +
            "                symbolIdx = time.indexOf('-')\n" +
            "            } else {\n" +
            "                return NaN\n" +
            "            }\n" +
            "            var minutes = time.substring(symbolIdx + 2)\n" +
            "            var hours = time.substring(symbolIdx + 1, symbolIdx + 2)\n" +
            "            var one = (time.charAt(symbolIdx) === '+') ? 1 : -1\n" +
            "            var intHr = one * parseInt(hours, 10) * 60 * 60 * 1000\n" +
            "            var intMin = one * parseInt(minutes, 10) * 60 * 1000\n" +
            "            var ms = minutes ? intHr + intMin : intHr\n" +
            "            return ms\n" +
            "        };\n" +
            "\n" +
            "        if (typeof exports === 'object') {\n" +
            "            module.exports = GeneralizedTime\n" +
            "        } else if (typeof define === 'function') {\n" +
            "            define(GeneralizedTime)\n" +
            "        } else {\n" +
            "            window.GeneralizedTime = GeneralizedTime\n" +
            "        }\n" +
            "    }())\n" +
            "\n" +
            "class Token {\n" +
            "    constructor(tokenInstance) {\n" +
            "        this.props = tokenInstance;\n" +
            "    }\n" +
            "\n" +
            "    formatGeneralizedTimeToDate(str) {\n" +
            "        const d = new GeneralizedTime(str);\n" +
            "        return new Date(d.getYear(), d.getMonth(), d.getDay(), d.getHours(), d.getMinutes(), d.getSeconds()).toLocaleDateString();\n" +
            "    }\n" +
            "\n" +
            "    formatGeneralizedTimeToTime(str) {\n" +
            "        const d = new GeneralizedTime(str);\n" +
            "        return new Date(d.getYear(), d.getMonth(), d.getDay(), d.getHours(), d.getMinutes(), d.getSeconds()).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});\n" +
            "    }\n" +
            "\n" +
            "    render() {\n" +
            "        let time;\n" +
            "        let date;\n" +
            "        if (this.props.time == null) {\n" +
            "            time = \"\";\n" +
            "            date = \"\";\n" +
            "        } else {\n" +
            "            time = this.formatGeneralizedTimeToTime(this.props.time.generalizedTime);\n" +
            "            date = this.props.time == null ? \"\": this.formatGeneralizedTimeToDate(this.props.time.generalizedTime);\n" +
            "        }\n" +
            "        return `&lt;div&gt;\n" +
            "            &lt;div&gt;\n" +
            "                &lt;span class=\"ts-count\"&gt;x${this.props._count}&lt;/span&gt;  &lt;span class=\"ts-category\"&gt;${this.props.name}&lt;/span&gt;\n" +
            "            &lt;/div&gt;\n" +
            "            &lt;div&gt;\n" +
            "                &lt;span class=\"ts-venue\"&gt;${this.props.building}&lt;/span&gt;\n" +
            "            &lt;/div&gt;\n" +
            "                &lt;div style=\"margin: 0px; padding:0px; clear: both; height: 6px\"&gt;\n" +
            "                &amp;nbsp;\n" +
            "                &lt;/div&gt;\n" +
            "            &lt;div&gt;\n" +
            "                &lt;img src=\"data:image/png;base64,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\" class=\"data-icon\"/&gt;\n" +
            "                &lt;span class=\"ts-date\"&gt;${date}&lt;/span&gt;\n" +
            "            &lt;/div&gt;\n" +
            "            &lt;div&gt;\n" +
            "                &lt;span class=\"ts-time\"&gt;${time}, ${this.props.locality}&lt;/span&gt;\n" +
            "            &lt;/div&gt;\n" +
            "            &lt;/div&gt;`;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "web3.tokens.dataChanged = (oldTokens, updatedTokens, tokenCardId) =&gt; {\n" +
            "    const currentTokenInstance = updatedTokens.currentInstance;\n" +
            "    document.getElementById(tokenCardId).innerHTML = new Token(currentTokenInstance).render();\n" +
            "};\n" +
            "\n" +
            "//\n" +
            "</script>\n" +
            "      </ts:view>\n" +
            "    </ts:card>\n" +
            "    <ts:card exclude=\"expired\" type=\"action\">\n" +
            "      <!-- this action is of the model confirm-back.\n" +
            "      It should be <ts:card type=\"action\" model=\"confirm-back\"> but Weiwu\n" +
            "      shied away from specifying that due to the likely change of design causing an upgrade path issue.\n" +
            "      window.onConfirm is called if user hit \"confirm\";\n" +
            "      window.close() causes the back button to be pressed.\n" +
            "      -->\n" +
            "      <ts:label>\n" +
            "        <ts:string xml:lang=\"en\">Enter</ts:string>\n" +
            "        <ts:string xml:lang=\"zh\">入場</ts:string>\n" +
            "        <ts:string xml:lang=\"es\">Entrar</ts:string>\n" +
            "      </ts:label>\n" +
            "      <ts:view xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">\n" +
            "        <style type=\"text/css\">.ts-count {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: bolder;\n" +
            "  font-size: 21px;\n" +
            "  color: rgb(117, 185, 67);\n" +
            "}\n" +
            ".ts-category {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 21px;\n" +
            "  color: rgb(67, 67, 67);\n" +
            "}\n" +
            ".ts-venue {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 16px;\n" +
            "  color: rgb(67, 67, 67);\n" +
            "}\n" +
            ".ts-date {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: bold;\n" +
            "  font-size: 14px;\n" +
            "  color: rgb(112, 112, 112);\n" +
            "  margin-left: 7px;\n" +
            "  margin-right: 7px;\n" +
            "}\n" +
            ".ts-time {\n" +
            "  font-family: \"SourceSansPro\";\n" +
            "  font-weight: lighter;\n" +
            "  font-size: 16px;\n" +
            "  color: rgb(112, 112, 112);\n" +
            "}\n" +
            "html {\n" +
            "}\n" +
            "\n" +
            "body {\n" +
            "padding: 0px;\n" +
            "margin: 0px;\n" +
            "}\n" +
            "\n" +
            "div {\n" +
            "margin: 0px;\n" +
            "padding: 0px;\n" +
            "}\n" +
            "\n" +
            ".data-icon {\n" +
            "height:16px;\n" +
            "vertical-align: middle\n" +
            "}\n" +
            "\n" +
            ".tbml-count {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: bolder;\u2028  font-size: 21px;\u2028  color: rgb(117, 185, 67);\u2028}\u2028.tbml-category {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 21px;\u2028  color: rgb(67, 67, 67);\u2028}\u2028.tbml-venue {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 16px;\u2028  color: rgb(67, 67, 67);\u2028}\u2028.tbml-date {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: bold;\u2028  font-size: 14px;\u2028  color: rgb(112, 112, 112);\u2028  margin-left: 7px;\u2028  margin-right: 7px;\u2028}\u2028.tbml-time {\u2028  font-family: \"SourceSansPro\";\u2028  font-weight: lighter;\u2028  font-size: 16px;\u2028  color: rgb(112, 112, 112);\u2028}\u2028   html {\u2028   }\u2028   \u2028   body {\u2028   padding: 0px;\u2028   margin: 0px;\u2028   }\u2028   \u2028   div {\u2028   margin: 0px;\u2028   padding: 0px;\u2028   }\u2028\u2028   .data-icon {\u2028   height:16px;\u2028   vertical-align: middle\u2028   }\u2028\n" +
            "\n" +
            "\n" +
            "</style>\n" +
            "        <script type=\"text/javascript\">//\n" +
            "class Token {\n" +
            "    constructor(tokenInstance) {\n" +
            "        this.props = tokenInstance\n" +
            "        document.getElementById(\"contractAddress\").value = this.props.EntryToken;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "web3.tokens.dataChanged = (oldTokens, updatedTokens, tokenCardId) =&gt; {\n" +
            "    const currentTokenInstance = updatedTokens.currentInstance;\n" +
            "    document.getElementById(tokenCardId).innerHTML = new Token(currentTokenInstance).render();\n" +
            "};\n" +
            "\n" +
            "document.addEventListener(\"DOMContentLoaded\", function() {\n" +
            "    window.onload = function startup() {\n" +
            "        // 1. call API to fetch challenge\n" +
            "        fetch('http://stormbird.duckdns.org:8080/api/getChallenge')\n" +
            "            .then(function (response) {\n" +
            "                return response.text()\n" +
            "            })\n" +
            "            .then(function (response) {\n" +
            "                document.getElementById('msg').innerHTML = 'Challenge: ' + response\n" +
            "                window.challenge = response\n" +
            "            })\n" +
            "    }\n" +
            "\n" +
            "    window.onConfirm = function onConfirm(signature) {\n" +
            "        if (window.challenge === undefined || window.challenge.length == 0) return\n" +
            "        const challenge = window.challenge\n" +
            "        document.getElementById('status').innerHTML = 'Wait for signature...'\n" +
            "        // 2. sign challenge to generate response\n" +
            "        web3.personal.sign({ data: challenge }, function (error, value) {\n" +
            "            if (error != null) {\n" +
            "                document.getElementById('status').innerHTML = error\n" +
            "                window.onload();\n" +
            "                return\n" +
            "            }\n" +
            "\n" +
            "            document.getElementById('status').innerHTML = 'Verifying credentials ...'\n" +
            "            // 3. open door\n" +
            "            let contractAddress = document.getElementById(\"contractAddress\").textContent;\n" +
            "            fetch(`http://stormbird.duckdns.org:8080/api/checkSignature?contract=${contractAddress}&amp;amp;challenge=${challenge}&amp;amp;sig=${value}`)\n" +
            "                .then(function (response) {\n" +
            "                    return response.text()\n" +
            "                })\n" +
            "                .then(function (response) {\n" +
            "                    if (response == \"pass\") {\n" +
            "                        document.getElementById('status').innerHTML = 'Entrance granted!'\n" +
            "                        window.close()\n" +
            "                    } else {\n" +
            "                        document.getElementById('status').innerHTML = 'Failed with: ' + response\n" +
            "                    }\n" +
            "                })\n" +
            "        });\n" +
            "        window.challenge = '';\n" +
            "        document.getElementById('msg').innerHTML = '';\n" +
            "    }\n" +
            "});\n" +
            "//\n" +
            "</script>\n" +
            "        <body><h3>Welcome to Craig Wright's house!</h3>\n" +
            "<div id=\"msg\">Preparing to unlock the entrance door.</div>\n" +
            "<div id=\"contractAddress\"></div>\n" +
            "<div id=\"status\"></div>\n" +
            "</body>\n" +
            "      </ts:view>\n" +
            "    </ts:card>\n" +
            "  </ts:cards>\n" +
            "    <ts:attribute name=\"locality\">\n" +
            "        <ts:type><ts:syntax>1.3.6.1.4.1.1466.115.121.1.15</ts:syntax></ts:type>\n" +
            "      <ts:origins>\n" +
            "        <ethereum:call as=\"utf8\" contract=\"EntryToken\" function=\"getLocality\">\n" +
            "            <ts:data>\n" +
            "              <ts:uint256 ref=\"tokenId\"></ts:uint256>\n" +
            "            </ts:data>\n" +
            "        </ethereum:call>\n" +
            "      </ts:origins>\n" +
            "\n" +
            "    </ts:attribute>\n" +
            "    <ts:attribute name=\"time\">\n" +
            "        <ts:type><ts:syntax>1.3.6.1.4.1.1466.115.121.1.24</ts:syntax></ts:type>\n" +
            "      <ts:label>\n" +
            "        <ts:string xml:lang=\"en\">Time</ts:string>\n" +
            "        <ts:string xml:lang=\"zh\">时间</ts:string>\n" +
            "      </ts:label>\n" +
            "      <ts:origins>\n" +
            "        <ts:token-id as=\"utf8\" bitmask=\"0000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000000000000000000\"></ts:token-id>\n" +
            "      </ts:origins>\n" +
            "    </ts:attribute>\n" +
            "    <ts:attribute name=\"expired\"> <!-- boolean -->\n" +
            "        <ts:type><ts:syntax>1.3.6.1.4.1.1466.115.121.1.7</ts:syntax></ts:type>\n" +
            "      <ts:origins>\n" +
            "        <ethereum:call as=\"bool\" contract=\"EntryToken\" function=\"isExpired\">\n" +
            "          <ts:data>\n" +
            "            <ts:uint256 ref=\"tokenId\"></ts:uint256>\n" +
            "          </ts:data>\n" +
            "        </ethereum:call>\n" +
            "      </ts:origins>\n" +
            "    </ts:attribute>\n" +
            "    <ts:attribute name=\"street\"> <!-- string -->\n" +
            "        <ts:type><ts:syntax>1.3.6.1.4.1.1466.115.121.1.15</ts:syntax></ts:type>\n" +
            "      <ts:origins>\n" +
            "        <ethereum:call as=\"utf8\" contract=\"EntryToken\" function=\"getStreet\">\n" +
            "          <ts:data>\n" +
            "            <ts:uint256 ref=\"tokenId\"></ts:uint256>\n" +
            "          </ts:data>\n" +
            "        </ethereum:call>\n" +
            "      </ts:origins>\n" +
            "    </ts:attribute>\n" +
            "    <ts:attribute name=\"building\"> <!-- string -->\n" +
            "        <ts:type><ts:syntax>1.3.6.1.4.1.1466.115.121.1.15</ts:syntax></ts:type>\n" +
            "      <ts:origins>\n" +
            "        <ethereum:call as=\"utf8\" contract=\"EntryToken\" function=\"getBuildingName\">\n" +
            "          <ts:data>\n" +
            "            <ts:uint256 ref=\"tokenId\"></ts:uint256>\n" +
            "          </ts:data>\n" +
            "        </ethereum:call>\n" +
            "      </ts:origins>\n" +
            "    </ts:attribute>\n" +
            "    <ts:attribute name=\"state\"> <!-- string -->\n" +
            "        <ts:type><ts:syntax>1.3.6.1.4.1.1466.115.121.1.15</ts:syntax></ts:type>\n" +
            "      <ts:origins>\n" +
            "        <ethereum:call as=\"utf8\" contract=\"EntryToken\" function=\"getState\">\n" +
            "          <ts:data>\n" +
            "            <ts:uint256 ref=\"tokenId\"></ts:uint256>\n" +
            "          </ts:data>\n" +
            "        </ethereum:call>\n" +
            "      </ts:origins>\n" +
            "    </ts:attribute>\n" +
            "</ts:token>\n";
    private final TokenDefinition entryToken;

    public TokenscriptFunctionTest() throws IOException, SAXException {
        InputStream fileStream = new ByteArrayInputStream(entryTokenTestFile.getBytes(StandardCharsets.UTF_8));
        entryToken = new TokenDefinition(
                fileStream,
                new Locale("en"),
                this
        );
    }

    @Test
    public void attributeTypesShouldExist() {
        assertTrue("should have a street attribute", entryToken.attributes.containsKey("street"));
        assertTrue("should have a building attribute", entryToken.attributes.containsKey("building"));
        assertTrue("should have a state attribute", entryToken.attributes.containsKey("state"));
    }

    @Test
    public void attributeTypesShouldBeUTF8() {
        As streetType = entryToken.attributes.get("street").as;
        As buildingType = entryToken.attributes.get("building").as;
        As stateType = entryToken.attributes.get("state").as;
        assertTrue("street, building & state should be a UTF8 origin",
                streetType.equals(As.UTF8) && buildingType.equals(As.UTF8) && stateType.equals(As.UTF8));
    }

    @Test
    public void testHoldingToken() {
        boolean hasHoldingToken = entryToken.contracts.containsKey(entryToken.holdingToken);
        boolean holdingTokenMatchesName = entryToken.holdingToken.equals("EntryToken");
        assertTrue("should contain a holdingToken", hasHoldingToken);
        assertTrue("should contain contract object matching the holding token name", holdingTokenMatchesName);
        boolean hasMainnet = entryToken.contracts.get(entryToken.holdingToken).addresses.containsKey(1L);
        assertTrue("underlying token has mainnet", hasMainnet);
        boolean correctContract = entryToken.contracts.get(entryToken.holdingToken).addresses.get(1L).contains("0x63ccef733a093e5bd773b41c96d3ece361464942");
        assertTrue("underlying token has the correct address", correctContract);
    }

    @Test
    public void entryTokenNameSpaceShouldBeCorrect() {
        String currentNamespace = "http://tokenscript.org/" + TOKENSCRIPT_MINIMUM_SCHEMA + "/tokenscript";
        assertTrue("should match the minimum namespace", entryToken.nameSpace.equals(currentNamespace));
    }

    @Override
    public void parseMessage(ParseResultId parseResult)
    {
        switch (parseResult)
        {
            case OK:
                Timber.d("Schema date is correct.");
                break;
            case XML_OUT_OF_DATE:
                Timber.d("Parsing outdated schema. It's an older schema but it checks out.");
                break;
            case PARSER_OUT_OF_DATE:
                Timber.d("Parser attempting to parse future schema. Code base needs to be updated.");
                fail();
                break;
            case PARSE_FAILED:
                Timber.d("Parser Error.");
                fail();
                break;
        }
    }
}
