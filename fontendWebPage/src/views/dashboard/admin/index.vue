<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData"/>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData"/>
    </el-row>

    <el-row :gutter="32">
      <div class="chart-wrapper">
        <pie-chart/>
      </div>
    </el-row>
    <ANewChart class-name="my-tree-chart" :width="'100%'" :height="'600px'"/>
  </div>
</template>

<script>

import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'

import PieChart from './components/PieChart'

import { fetchFilteredVisData } from '@/api/libraryData'
import ANewChart from './components/ANewChart.vue'

export default {
  name: 'DashboardAdmin',
  components: {
    PanelGroup,
    LineChart,
    PieChart,
    ANewChart
  },
  data() {
    return {
      lineChartData: null
    }
  },
  mounted() {
    this.fetchLineChartData(1)
  },
  methods: {
    fetchLineChartData(type) {
      fetchFilteredVisData({ floorNumber: type }).then(response => {
        this.lineChartData = response
      }).catch(error => {
        console.error('Error fetching data:', error)
      })
    },
    handleSetLineChartData(type) {
      this.fetchLineChartData(type)
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
