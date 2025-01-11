<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'

require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import { fetchTreeJson } from '@/api/libraryData'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  data() {
    return {
      chartData: [],
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.fetchANewChartData()
      this.initChart()
    })
  },

  methods: {
    fetchANewChartData() {
      Promise.all([
        fetchTreeJson()
      ]).then(responses => {
        this.chartData = responses[0]
        this.initChart() // 在这里调用 initChart
      }).catch(error => {
        console.error('Error fetching data:', error)
      })
    },

    setOptions(chartData) {
      console.log(chartData)
      this.chart.setOption({
        series: [
          {
            type: 'treemap',
            data: chartData,
            label: {
              show: true,
              formatter: function(params) {
                // 显示 value 属性
                return params.data.value
              }
            }
          }
        ]
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    }
  }
}
</script>
