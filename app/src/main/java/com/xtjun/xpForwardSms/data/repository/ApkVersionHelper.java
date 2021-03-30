package com.xtjun.xpForwardSms.data.repository;

import androidx.core.text.HtmlCompat;

import com.xtjun.xpForwardSms.data.http.entity.ApkVersion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ApkVersionHelper {

    private ApkVersionHelper() {
    }

    static ApkVersion parseFromCoolApk(String html) {
        Document document = Jsoup.parse(html);
        String versionName = "-1";
        String versionInfo = null;
        if (document != null) {
            Element element = document.selectFirst("title");
            if (element != null) {
                String text = element.text();
                Pattern p = Pattern.compile("\\d(\\.\\d)+");
                Matcher m = p.matcher(text);
                if (m.find()) {
                    versionName = m.group();
                }
            }

            Element rootInfoEle = document.selectFirst(".apk_left_title:contains(新版特性)");
            if (rootInfoEle != null) {
                Element infoEle = rootInfoEle.selectFirst(".apk_left_title_info");
                if (infoEle != null) {
                    versionInfo = HtmlCompat.fromHtml(infoEle.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
                            .toString().trim();
                }
            }
        }
        return new ApkVersion(versionName, versionInfo);
    }

}
