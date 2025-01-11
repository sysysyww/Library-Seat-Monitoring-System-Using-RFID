<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('1')">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="1" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            1st floor
          </div>
          <count-to :start-val="0" :end-val="libraryData.floor1.emptySeats" :duration="2600" class="card-panel-num"/>
          /
          <count-to :start-val="0" :end-val="libraryData.floor1.totalSeats" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('2')">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="2" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            2nd floor
          </div>
          <count-to :start-val="0" :end-val="libraryData.floor2.emptySeats" :duration="3000" class="card-panel-num"/>
          /
          <count-to :start-val="0" :end-val="libraryData.floor2.totalSeats" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('3')">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="3" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            3rd floor
          </div>
          <count-to :start-val="0" :end-val="libraryData.floor3.emptySeats" :duration="3200" class="card-panel-num"/>
          /
          <count-to :start-val="0" :end-val="libraryData.floor3.totalSeats" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('4')">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="4" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            4th floor
          </div>
          <count-to :start-val="0" :end-val="libraryData.floor4.emptySeats" :duration="3600" class="card-panel-num"/>
          /
          <count-to :start-val="0" :end-val="libraryData.floor4.totalSeats" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
import { fetchFilteredVisData, fetchFilteredVisDataReversed } from '@/api/libraryData'

export default {
  components: {
    CountTo
  },
  data() {
    return {
      libraryData: {
        floor1: {
          emptySeats: 0,
          totalSeats: 0
        },
        floor2: {
          emptySeats: 0,
          totalSeats: 0
        },
        floor3: {
          emptySeats: 0,
          totalSeats: 0
        },
        floor4: {
          emptySeats: 0,
          totalSeats: 0
        }
      }
    }
  },
  mounted() {
    this.getfloor1Data()
    this.getfloor2Data()
    this.getfloor3Data()
    this.getfloor4Data()
  },
  methods: {
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    },
    getfloor1Data() {
      // API 请求获取 newVisits 数据
      fetchFilteredVisDataReversed({
        floorNumber: 1
      }).then(response => {
        const data = response[0]
        this.libraryData.floor1.emptySeats = data.emptySeats
        this.libraryData.floor1.totalSeats = data.totalSeats
      }).catch(error => {
        console.error('Error fetching floor1 data:', error.response)
      })
    },
    getfloor2Data() {
      // API 请求获取 newVisits 数据
      fetchFilteredVisDataReversed({
        floorNumber: 2
      }).then(response => {
        const data = response[0]
        this.libraryData.floor2.emptySeats = data.emptySeats
        this.libraryData.floor2.totalSeats = data.totalSeats
      }).catch(error => {
        console.error('Error fetching floor2 data:', error)
      })
    },
    getfloor3Data() {
      // API 请求获取 newVisits 数据
      fetchFilteredVisDataReversed({
        floorNumber: 3
      }).then(response => {
        const data = response[0]
        this.libraryData.floor3.emptySeats = data.emptySeats
        this.libraryData.floor3.totalSeats = data.totalSeats
      }).catch(error => {
        console.error('Error fetching floor3 data:', error)
      })
    },
    getfloor4Data() {
      // API 请求获取 newVisits 数据
      fetchFilteredVisDataReversed({
        floorNumber: 4
      }).then(response => {
        const data = response[0]
        this.libraryData.floor4.emptySeats = data.emptySeats
        this.libraryData.floor4.totalSeats = data.totalSeats
      }).catch(error => {
        console.error('Error fetching floor4 data:', error)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
