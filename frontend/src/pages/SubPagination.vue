<template>
  <q-page class="flex flex-center" style="padding: 0px;">
    <q-table
      title="Teste Sub-Paginação Server Side"
      :loading="isLoading"
      :data="result"
      selection="none"
      no-results-label="Nenhum processo encontrado."
      no-data-label="Nenhum processo encontrado."
      :rows-per-page-options="rowsPerPageOptions"
      :pagination.sync="paginationOptions"
      @request="requestData"
    />
  </q-page>
</template>

<script>
export default {
  name: 'SubPagination',
  props: ['clientId', 'fetchData'],
  data: () => {
    return {
      rowsPerPageOptions: [10],
      isLoading: false,
      paginationOptions: {
        sortBy: 'id',
        descending: false,
        page: 1,
        rowsPerPage: 10,
        rowsNumber: 0
      },
      columns: [
        {
          name: 'id',
          label: 'ID',
          field: 'id',
          required: true,
          sortable: true,
          align: 'left'
        },
        {
          name: 'description',
          label: 'Descrição',
          field: 'description',
          required: true,
          sortable: true,
          align: 'left'
        },
        {
          name: 'creationDate',
          label: 'Data de Criação',
          field: 'creationDate',
          required: true,
          sortable: true,
          align: 'left'
        },
        {
          name: 'amount',
          label: 'Quantidade',
          field: 'amount',
          required: true,
          sortable: true,
          align: 'left'
        }
      ],
      result: []
    }
  },
  methods: {
    requestData (requestProp) {
      const quasar = this.$q

      this.isLoading = true
      quasar.loading.show({
        message: 'Carregando dados...'
      })

      const pagination = requestProp.pagination
      console.log(pagination)

      const page = pagination.page - 1
      const size = pagination.rowsPerPage
      const sortBy = pagination.sortBy === null ? this.paginationOptions.sortBy : pagination.sortBy

      pagination.descending = pagination.sortBy === null ? !pagination.descending : pagination.descending
      const descending = pagination.descending ? 'desc' : 'asc'

      this.$axios({
        method: 'GET',
        url: `http://localhost:8089/clients/${this.clientId}/transactions?page=${page}&size=${size}&sort=${sortBy},${descending}`
      }).then((response) => {
        console.log(response)
        const data = response.data

        this.result = data.content
        this.paginationOptions.page = pagination.page
        this.paginationOptions.rowsPerPage = pagination.rowsPerPage
        this.paginationOptions.sortBy = pagination.sortBy === null ? this.paginationOptions.sortBy : pagination.sortBy
        this.paginationOptions.descending = pagination.descending
        this.paginationOptions.rowsNumber = data.totalElements
      }).catch((error) => {
        console.log(error)
        quasar.notify({ type: 'negative', message: 'Nao foi possivel obter os dados.' })
      }).finally(() => {
        this.isLoading = false
        quasar.loading.hide()
      })
    }
  },
  watch: {
    fetchData () {
      if (this.fetchData) {
        this.requestData({ pagination: this.paginationOptions })
      }
    }
  }
}
</script>

<style scoped>

</style>
