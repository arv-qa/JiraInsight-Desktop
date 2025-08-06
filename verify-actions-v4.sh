#!/bin/bash

echo "========================================"
echo "GitHub Actions v4 Verification Script"
echo "========================================"

# Check if workflow files exist
WORKFLOW_DIR=".github/workflows"
if [ ! -d "$WORKFLOW_DIR" ]; then
    echo "❌ ERROR: .github/workflows directory not found"
    exit 1
fi

echo "✅ Workflow directory found"
echo ""

# List of workflow files to check
WORKFLOW_FILES=(
    "ci-cd.yml"
    "code-quality.yml"
    "dependency-update.yml"
    "performance.yml"
    "release.yml"
)

echo "🔍 Checking workflow files for Actions v4 updates..."
echo ""

# Check each workflow file
for file in "${WORKFLOW_FILES[@]}"; do
    filepath="$WORKFLOW_DIR/$file"
    
    if [ ! -f "$filepath" ]; then
        echo "⚠️  WARNING: $file not found"
        continue
    fi
    
    echo "📄 Checking $file:"
    
    # Check for v3 actions (should be none)
    v3_count=$(grep -c "actions/.*@v3" "$filepath" 2>/dev/null || echo "0")
    
    # Check for v4 actions
    v4_upload_count=$(grep -c "actions/upload-artifact@v4" "$filepath" 2>/dev/null || echo "0")
    v4_cache_count=$(grep -c "actions/cache@v4" "$filepath" 2>/dev/null || echo "0")
    
    if [ "$v3_count" -gt 0 ]; then
        echo "   ❌ Found $v3_count v3 actions (should be updated)"
        grep -n "actions/.*@v3" "$filepath"
    else
        echo "   ✅ No v3 actions found"
    fi
    
    if [ "$v4_upload_count" -gt 0 ]; then
        echo "   ✅ Found $v4_upload_count upload-artifact@v4 actions"
    fi
    
    if [ "$v4_cache_count" -gt 0 ]; then
        echo "   ✅ Found $v4_cache_count cache@v4 actions"
    fi
    
    echo ""
done

echo "========================================"
echo "📊 Summary Report"
echo "========================================"

# Count total v3 and v4 actions across all files
total_v3=$(grep -r "actions/.*@v3" "$WORKFLOW_DIR" 2>/dev/null | wc -l)
total_v4_upload=$(grep -r "actions/upload-artifact@v4" "$WORKFLOW_DIR" 2>/dev/null | wc -l)
total_v4_cache=$(grep -r "actions/cache@v4" "$WORKFLOW_DIR" 2>/dev/null | wc -l)

echo "📈 Actions Version Statistics:"
echo "   • v3 actions found: $total_v3"
echo "   • v4 upload-artifact actions: $total_v4_upload"
echo "   • v4 cache actions: $total_v4_cache"
echo ""

if [ "$total_v3" -eq 0 ]; then
    echo "🎉 SUCCESS: All actions updated to v4!"
    echo "✅ No v3 actions remaining"
    echo "✅ CI/CD pipeline is using latest GitHub Actions"
else
    echo "⚠️  WARNING: $total_v3 v3 actions still found"
    echo "❌ Update required for remaining v3 actions"
fi

echo ""
echo "🔧 Expected v4 Action Counts:"
echo "   • upload-artifact@v4: 9 actions"
echo "   • cache@v4: 2 actions"
echo ""

if [ "$total_v4_upload" -eq 9 ] && [ "$total_v4_cache" -eq 2 ]; then
    echo "✅ PERFECT: All expected v4 actions are present!"
else
    echo "⚠️  Note: Action counts may vary based on workflow configuration"
fi

echo ""
echo "========================================"
echo "🚀 Next Steps"
echo "========================================"
echo "1. Review the verification results above"
echo "2. If all actions are v4, commit the changes:"
echo "   • Windows: commit-cicd-pipeline.bat"
echo "   • Linux/Mac: ./commit-cicd-pipeline.sh"
echo "3. Push to GitHub to activate updated workflows"
echo "4. Monitor first workflow run for any issues"
echo ""
echo "📚 Documentation:"
echo "   • ACTIONS_V4_UPDATE_SUMMARY.md - Detailed update summary"
echo "   • CICD_PIPELINE_GUIDE.md - Complete pipeline documentation"
echo ""
