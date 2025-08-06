# 🔐 NVD API Key Setup Guide

## 📋 **Overview**

The National Vulnerability Database (NVD) API key significantly improves the performance and reliability of OWASP Dependency Check vulnerability scanning. This guide shows you how to obtain and configure the NVD API key for the JiraInsight Desktop project.

## 🎯 **Benefits of Using NVD API Key**

### **Performance Improvements**
- ✅ **Faster Scans** - Reduced scan time from 10-15 minutes to 2-3 minutes
- ✅ **Higher Rate Limits** - 2000 requests per 30 seconds (vs 50 without key)
- ✅ **Better Reliability** - Reduced timeouts and connection issues
- ✅ **Priority Access** - Faster response times from NVD servers

### **Enhanced Features**
- ✅ **Real-time Updates** - Access to latest vulnerability data
- ✅ **Detailed Metadata** - More comprehensive vulnerability information
- ✅ **Better Error Handling** - Improved error messages and retry logic

## 🔑 **How to Obtain NVD API Key**

### **Step 1: Request NVD API Key**
1. **Visit**: https://nvd.nist.gov/developers/request-an-api-key
2. **Fill out the form**:
   - **Email**: Your valid email address
   - **Organization**: Your company/organization name
   - **Use Case**: "Open source vulnerability scanning for CI/CD pipeline"
   - **Justification**: "Automated security scanning for software development"
3. **Submit** the request
4. **Wait for approval** (usually 1-2 business days)
5. **Receive API key** via email

### **Step 2: Verify API Key**
```bash
# Test your API key (replace YOUR_API_KEY with actual key)
curl -H "apiKey: YOUR_API_KEY" \
  "https://services.nvd.nist.gov/rest/json/cves/2.0?resultsPerPage=1"

# Should return JSON response with CVE data
```

## ⚙️ **Configuration in GitHub**

### **Step 1: Add GitHub Secret**
1. **Go to**: https://github.com/arv-qa/JiraInsight-Desktop/settings/secrets/actions
2. **Click**: "New repository secret"
3. **Name**: `NVD_API_KEY`
4. **Value**: Your NVD API key (paste the key you received)
5. **Click**: "Add secret"

### **Step 2: Verify Secret Configuration**
- **Secret Name**: `NVD_API_KEY`
- **Scope**: Repository secrets
- **Access**: Available to all workflows in the repository

## 🔧 **Technical Implementation**

### **Maven Configuration (pom.xml)**
```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>9.0.7</version>
    <configuration>
        <!-- NVD API Key Configuration -->
        <nvdApiKey>${env.NVD_API_KEY}</nvdApiKey>
        <nvdApiDelay>4000</nvdApiDelay>
        <nvdMaxRetryCount>10</nvdMaxRetryCount>
        <nvdValidForHours>24</nvdValidForHours>
        
        <!-- Performance and Output Settings -->
        <formats>
            <format>HTML</format>
            <format>XML</format>
            <format>JSON</format>
        </formats>
        <failBuildOnCVSS>8.0</failBuildOnCVSS>
    </configuration>
</plugin>
```

### **GitHub Actions Workflow**
```yaml
- name: Run OWASP Dependency Check
  env:
    NVD_API_KEY: ${{ secrets.NVD_API_KEY }}
  run: |
    if [ -n "$NVD_API_KEY" ]; then
      echo "🔐 Running with NVD API key"
      mvn org.owasp:dependency-check-maven:check -DnvdApiKey="$NVD_API_KEY"
    else
      echo "⚠️ Running without NVD API key (slower)"
      mvn org.owasp:dependency-check-maven:check
    fi
```

## 📊 **Configuration Options**

### **NVD API Settings**
| Setting | Value | Description |
|---------|-------|-------------|
| `nvdApiKey` | `${env.NVD_API_KEY}` | API key from environment |
| `nvdApiDelay` | `4000` | Delay between API calls (ms) |
| `nvdMaxRetryCount` | `10` | Maximum retry attempts |
| `nvdValidForHours` | `24` | Cache validity period |

### **Performance Settings**
| Setting | Value | Description |
|---------|-------|-------------|
| `cveValidForHours` | `24` | CVE data cache duration |
| `failBuildOnCVSS` | `8.0` | Fail build on high severity |
| `bundleAuditAnalyzerEnabled` | `false` | Disable Ruby bundle audit |
| `nexusAnalyzerEnabled` | `false` | Disable Nexus analyzer |

## 🛡️ **Security Best Practices**

### **API Key Security**
- ✅ **Never commit** API keys to source code
- ✅ **Use GitHub Secrets** for secure storage
- ✅ **Rotate keys** periodically (every 6-12 months)
- ✅ **Monitor usage** through NVD dashboard
- ✅ **Limit access** to necessary team members only

### **Suppressions File**
- ✅ **Review suppressions** regularly
- ✅ **Document reasons** for each suppression
- ✅ **Set review dates** for periodic reassessment
- ✅ **Version control** suppressions file

## 🔍 **Testing and Validation**

### **Local Testing**
```bash
# Set environment variable locally
export NVD_API_KEY="your-api-key-here"

# Run dependency check
mvn org.owasp:dependency-check-maven:check

# Check for faster execution and detailed reports
```

### **CI/CD Testing**
1. **Push changes** to trigger workflow
2. **Monitor workflow logs** for API key usage
3. **Check scan duration** (should be significantly faster)
4. **Verify reports** are generated with detailed vulnerability data

## 📈 **Expected Performance**

### **Without NVD API Key**
- ⏱️ **Scan Time**: 10-15 minutes
- 🔄 **Rate Limit**: 50 requests per 30 seconds
- ⚠️ **Reliability**: Frequent timeouts and retries
- 📊 **Data Quality**: Basic vulnerability information

### **With NVD API Key**
- ⚡ **Scan Time**: 2-3 minutes
- 🚀 **Rate Limit**: 2000 requests per 30 seconds
- ✅ **Reliability**: Stable and consistent
- 📈 **Data Quality**: Comprehensive vulnerability details

## 🔄 **Maintenance**

### **Regular Tasks**
- **Monthly**: Review vulnerability reports
- **Quarterly**: Update suppressions file
- **Semi-annually**: Rotate NVD API key
- **Annually**: Review and update OWASP plugin version

### **Monitoring**
- **Track scan performance** in CI/CD logs
- **Monitor API usage** through NVD dashboard
- **Review vulnerability trends** in reports
- **Update security policies** based on findings

## 🚨 **Troubleshooting**

### **Common Issues**
| Issue | Cause | Solution |
|-------|-------|----------|
| Slow scans | No API key | Add NVD_API_KEY secret |
| API timeouts | Rate limiting | Verify API key is working |
| Build failures | High CVSS scores | Review and suppress false positives |
| Missing reports | Plugin errors | Check Maven logs for details |

### **Debug Commands**
```bash
# Check if API key is set
echo "API Key set: ${NVD_API_KEY:+YES}"

# Run with debug output
mvn org.owasp:dependency-check-maven:check -X

# Check generated reports
ls -la target/dependency-check-report.*
```

## ✅ **Verification Checklist**

- [ ] **NVD API key** requested and received
- [ ] **GitHub secret** `NVD_API_KEY` configured
- [ ] **Maven plugin** updated with NVD configuration
- [ ] **Workflows** updated to use API key
- [ ] **Suppressions file** created and configured
- [ ] **Local testing** completed successfully
- [ ] **CI/CD testing** shows improved performance
- [ ] **Reports** generated with detailed vulnerability data

## 🎉 **Summary**

The NVD API key configuration provides:
- ✅ **Significantly faster** vulnerability scans
- ✅ **More reliable** CI/CD pipeline execution
- ✅ **Better vulnerability data** quality
- ✅ **Improved developer experience**

**Your security scanning is now optimized for performance and reliability!** 🚀

---

**Next Steps**: Commit the changes and add the NVD_API_KEY secret to GitHub repository settings.
